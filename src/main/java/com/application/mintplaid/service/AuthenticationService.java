package com.application.mintplaid.service;

import com.application.mintplaid.config.Environment;
import com.application.mintplaid.config.JwtService;
import com.application.mintplaid.dto.*;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.entity.RefreshToken;
import com.application.mintplaid.entity.Role;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.plaid.exchange_token.ExchangeTokenRequestContract;
import com.application.mintplaid.plaid.exchange_token.ExchangeTokenResponseContract;
import com.application.mintplaid.plaid.exchange_token.PublicTokenRequest;
import com.application.mintplaid.repository.ItemRepository;
import com.application.mintplaid.repository.RefreshTokenRepository;
import com.application.mintplaid.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ItemRepository itemRepository;

    private final JavaMailSender mailSender;

    public RegisterResponse register(@NotNull RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .actionRequired(User.Action.Initial)
                .enabled(false)
                .build();

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()
                && userRepository.findUserByUsername(registerRequest.getUsername()).isPresent()) {
            return RegisterResponse.builder().responseCode(409).message("Email or username is already exist").build();
        }
        user.setVerificationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendVerificationEmail(user);
        return RegisterResponse.builder().responseCode(200).message("The Registration is successful").build();
    }

    public AuthenticationResponse authentication(@NotNull AuthenticationRequest authenticationRequest) throws URISyntaxException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userRepository.findUserByUsername(authenticationRequest.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = createRefreshToken(user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken).refreshToken(refreshToken.getRefreshToken()).build();
    }

    public boolean exchangePublicToken (@NotNull PublicTokenRequest publicTokenRequest) throws URISyntaxException {
        ExchangeTokenRequestContract exchangeTokenRequestContract = new ExchangeTokenRequestContract();
        exchangeTokenRequestContract.setClientId(Environment.sandboxClientId);
        exchangeTokenRequestContract.setSecret(Environment.sandboxSecret);
        exchangeTokenRequestContract.setPublicToken(publicTokenRequest.getPublicToken());
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(Environment.sandboxEnv + "/item/public_token/exchange");
        ResponseEntity<ExchangeTokenResponseContract> response =
                restTemplate.postForEntity(uri, exchangeTokenRequestContract, ExchangeTokenResponseContract.class);
        if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
            ExchangeTokenResponseContract exchangeTokenResponseContract = response.getBody();
            Item item = Item.builder()
                    .itemId(exchangeTokenResponseContract.getItemId())
                    .accessToken(exchangeTokenResponseContract.getAccessToken())
                    .build();
            User user = userRepository.findUserByUsername(publicTokenRequest.getUsername()).orElseThrow();
            user.setItemId(item.getItemId());
            user.setActionRequired(User.Action.None);
            userRepository.save(user);
            itemRepository.save(item);
            return true;
        }
        return false;
    }

    //Refresh Token
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(refreshToken::setUser);
        refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(@NotNull RefreshToken token) throws TokenRefreshException {
        if (token.getExpiry().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken(),
                    "Refresh token was expired. Please make a new sign-in request");
        }

        return token;
    }

    public TokenRefreshResponse refreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    refreshTokenRepository.deleteByUser(user);
                    return new TokenRefreshResponse(jwtService.generateToken(user),
                            createRefreshToken(user.getId()).getRefreshToken());
                }).orElseThrow(() -> new TokenRefreshException(token, "Refresh token does not exist or has expired"));
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    public void sendVerificationEmail(@NotNull User user) throws MessagingException, UnsupportedEncodingException {
        String clientEmail = user.getEmail();
        String fromAddress = "quangdinhtesting@gmail.com";
        String senderName = "MintPlaidByQ";
        String subject = "Please verify your registration";
        String content = "Dear " + user.getUsername()
                + ", <br>" + "Please click the link below to verify your registration: <br>"
                + "<h3> <a href=\"" + Environment.localDevelopment + "/verify?code=" + user.getVerificationCode() + "\" target=\"_self\"> VERIFY </a></h3>"
                + "Thank you, <br>"
                + senderName;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(clientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public String verifyEmail(String verificationCode) {
        Optional<User> optionalUser = userRepository.findUserByVerificationCode(verificationCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            return "User Email Verification is completed. You can now close this tab";
        }
        return "The Verification Code is invalid, please register again";
    }

}
