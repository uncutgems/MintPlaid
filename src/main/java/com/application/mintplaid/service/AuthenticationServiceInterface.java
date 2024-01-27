package com.application.mintplaid.service;

import com.application.mintplaid.dto.AuthenticationRequest;
import com.application.mintplaid.dto.AuthenticationResponse;
import com.application.mintplaid.dto.RegisterRequest;

import java.net.URISyntaxException;

public interface AuthenticationServiceInterface {
    public AuthenticationResponse register(RegisterRequest registerRequest) throws URISyntaxException;
    public AuthenticationResponse authentication(AuthenticationRequest registerRequest);

}
