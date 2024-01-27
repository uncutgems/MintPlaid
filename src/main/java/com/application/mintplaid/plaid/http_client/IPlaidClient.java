package com.application.mintplaid.plaid.http_client;

import com.application.mintplaid.plaid.balance.AccountBalanceRequest;
import com.application.mintplaid.plaid.transaction.PlaidTransactionRequest;
import com.application.mintplaid.plaid.transaction.PlaidTransactionResponse;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

public interface IPlaidClient {
    CompletableFuture<?> getAccountBalanceAsync(AccountBalanceRequest requestBody) throws URISyntaxException;

    CompletableFuture<PlaidTransactionResponse> getTransactionAsync(PlaidTransactionRequest requestBody) throws URISyntaxException;
}
