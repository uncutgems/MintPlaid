package com.application.mintplaid.controller;

import com.application.mintplaid.dto.*;
import com.application.mintplaid.plaid.exchange_token.PublicTokenRequest;
import com.application.mintplaid.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (@RequestBody RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest authenticationRequest) throws URISyntaxException {
        return new ResponseEntity<>(authenticationService.authentication(authenticationRequest), HttpStatus.OK);
    }


    @PostMapping("/exchangeToken")
    public ResponseEntity<Boolean> exchangeToken(@RequestBody PublicTokenRequest publicTokenRequest) throws URISyntaxException {
        return new ResponseEntity<>(authenticationService.exchangePublicToken(publicTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return new ResponseEntity<>(authenticationService.refreshToken(tokenRefreshRequest.getRefreshToken()), HttpStatus.OK);
    }

}