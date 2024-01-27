package com.application.mintplaid.controller;

import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.service.PlaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/plaid-sync")
@RequiredArgsConstructor
public class PlaidSyncController {
    private final PlaidService plaidService;
    @PostMapping("/initial")
    public ResponseEntity<LinkPlaidResponse> initialPlaidLink() throws URISyntaxException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LinkPlaidResponse linkPlaidResponse = plaidService.initialLink(LinkPlaidRequest.builder().email(user.getEmail()).build());
        return new ResponseEntity<>(linkPlaidResponse, HttpStatus.OK);
    }

    @PostMapping("/fix")
    public ResponseEntity<LinkPlaidResponse> fixPlaidLink() throws URISyntaxException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LinkPlaidResponse linkPlaidResponse = plaidService.fixLink(LinkPlaidRequest.builder().email(user.getEmail()).build());
        return new ResponseEntity<>(linkPlaidResponse, HttpStatus.OK);
    }
}
