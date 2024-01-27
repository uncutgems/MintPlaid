package com.application.mintplaid.service;

import com.application.mintplaid.config.Environment;
import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.plaid.exchange_token.LinkTokenRequest;
import com.application.mintplaid.plaid.exchange_token.PlaidUserContract;
import com.application.mintplaid.repository.ItemRepository;
import com.application.mintplaid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaidService implements PlaidServiceInterface{
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    @Override
    public LinkPlaidResponse initialLink(@NotNull LinkPlaidRequest plaidRequest) throws URISyntaxException {
        Optional<User> optionalUser = userRepository.findByEmail(plaidRequest.getEmail());
        LinkPlaidResponse linkPlaidResponse = new LinkPlaidResponse();
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            URI uri = new URI(Environment.sandboxEnv + "/link/token/create");
            RestTemplate restTemplate = new RestTemplate();
            PlaidUserContract plaidUserContract = PlaidUserContract.builder()
                    .clientUserId(user.getUsername()).build();
            LinkTokenRequest linkTokenRequest = LinkTokenRequest.builder()
                    .clientId(Environment.sandboxClientId)
                    .secret(Environment.sandboxSecret)
                    .clientName(Environment.appName)
                    .language("en")
                    .countryCodes(Environment.countries)
                    .plaidUserContract(plaidUserContract)
                    .products(Environment.products)
                    .build();
            ResponseEntity<LinkPlaidResponse> response =
                    restTemplate.postForEntity(uri, linkTokenRequest, LinkPlaidResponse.class);
            linkPlaidResponse = response.getBody();
        }
        return linkPlaidResponse;
    }

    @Override
    public LinkPlaidResponse fixLink(@NotNull LinkPlaidRequest plaidRequest) throws URISyntaxException {
        Optional<User> optionalUser = userRepository.findByEmail(plaidRequest.getEmail());
        LinkPlaidResponse linkPlaidResponse = new LinkPlaidResponse();
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            URI uri = new URI(Environment.sandboxEnv + "/link/token/create");
            Item item = itemRepository.findByItemId(user.getItemId()).orElseThrow();
            RestTemplate restTemplate = new RestTemplate();
            PlaidUserContract plaidUserContract = PlaidUserContract.builder()
                    .clientUserId(user.getUsername()).build();
            LinkTokenRequest linkTokenRequest = LinkTokenRequest.builder()
                    .clientId(Environment.sandboxClientId)
                    .secret(Environment.sandboxSecret)
                    .clientName(Environment.appName)
                    .language("en")
                    .countryCodes(Environment.countries)
                    .plaidUserContract(plaidUserContract)
                    .products(Environment.products)
                    .accessToken(item.getAccessToken())
                    .build();
            ResponseEntity<LinkPlaidResponse> response =
                    restTemplate.postForEntity(uri, linkTokenRequest, LinkPlaidResponse.class);
            linkPlaidResponse = response.getBody();
        }
        return linkPlaidResponse;
    }
}
