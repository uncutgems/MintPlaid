package com.application.mintplaid.controller;

import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.service.AccountServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/get-balance")
@RequiredArgsConstructor
public class BalanceController {
    private final AccountServiceInterface accountService;

    @GetMapping("/all-account")
    public ResponseEntity<List<Account>> getAllAccountDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String itemId = user.getItemId();
        return new ResponseEntity<>(accountService.getAllAccountDetails(itemId), HttpStatus.OK);
    }
}
