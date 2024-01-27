package com.application.mintplaid.service;

import com.application.mintplaid.config.Environment;
import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.Transaction;
import com.application.mintplaid.plaid.PlaidAccountContract;
import com.application.mintplaid.plaid.transaction.PlaidTransactionContract;
import com.application.mintplaid.plaid.transaction.PlaidTransactionRequest;
import com.application.mintplaid.plaid.transaction.PlaidTransactionResponse;
import com.application.mintplaid.repository.AccountRepository;
import com.application.mintplaid.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionServiceInterface{
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;
    private static final String dateFormat = "dd-MM-yyyy";

    @Override
    public List<Transaction> findTransactionByDate(String itemId, Date startDate, Date endDate) {
        List<Account> accounts = accountRepository.getAccountByItem(itemId);
        List<String> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getPlaidAccountId());
        }
        return transactionRepository.findAllByPlaidAccountInAndDateBetween(accountIds, startDate, endDate);
    }

    @Override
    public List<Transaction> getAllTransactionHistory(String itemId) {
        List<Account> accounts = accountRepository.getAccountByItem(itemId);
        List<String> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getPlaidAccountId());
        }
        return transactionRepository.findByPlaidAccountIn(accountIds);
    }


    @Override
    public String populateTransaction(String accessToken) throws URISyntaxException, ParseException {
        PlaidTransactionRequest plaidTransactionRequest = new PlaidTransactionRequest();
        plaidTransactionRequest.setAccessToken(accessToken);
        plaidTransactionRequest.setClientId(Environment.sandboxClientId);
        plaidTransactionRequest.setSecret(Environment.sandboxSecret);
        plaidTransactionRequest.setStartDate("2021-08-23");
        plaidTransactionRequest.setEndDate("2023-04-11");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(Environment.sandboxEnv + "/transactions/get");
        ResponseEntity<PlaidTransactionResponse> response =
                restTemplate.postForEntity(uri, plaidTransactionRequest, PlaidTransactionResponse.class);
        if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
            for (PlaidAccountContract plaidAccount : response.getBody().getAccounts()) {
                Account account = accountRepository.getAccountByPlaidAccountId(plaidAccount.getAccountId());
                if (account != null) {
                    account.setCurrentBalance(plaidAccount.getBalances().getCurrent());
                    account.setAvailableBalance(plaidAccount.getBalances().getAvailable());
                    account.setName(plaidAccount.getName());
                    account.setType(plaidAccount.getType());
                    account.setSubtype(plaidAccount.getSubtype());
                    account.setItem(response.getBody().getItem().getItemId());
                } else {
                    account = Account.builder()
                            .plaidAccountId(plaidAccount.getAccountId())
                            .currentBalance(plaidAccount.getBalances().getCurrent())
                            .availableBalance(plaidAccount.getBalances().getAvailable())
                            .name(plaidAccount.getName())
                            .type(plaidAccount.getType())
                            .subtype(plaidAccount.getSubtype())
                            .item(response.getBody().getItem().getItemId())
                            .build();
                }
                accountRepository.save(account);
            }
            for (PlaidTransactionContract plaidTransaction : response.getBody().getTransactions()) {
                Transaction transaction = transactionRepository.
                        findByPlaidTransactionId(plaidTransaction.getTransactionId());
                if (transaction != null) {
                    transaction.setDate(new SimpleDateFormat(dateFormat).parse(plaidTransaction.getDate()));
                    transaction.setAmount(plaidTransaction.getAmount());
                    transaction.setPending(plaidTransaction.getPending());
                    transaction.setMerchantName(plaidTransaction.getMerchantName());
                    transaction.setPaymentChannel(plaidTransaction.getPaymentChannel());
                } else {
                    transaction = Transaction.builder()
                            .plaidTransactionId(plaidTransaction.getTransactionId())
                            .date(new SimpleDateFormat(dateFormat).parse(plaidTransaction.getDate()))
                            .amount(plaidTransaction.getAmount())
                            .pending(plaidTransaction.getPending())
                            .merchantName(plaidTransaction.getMerchantName())
                            .paymentChannel(plaidTransaction.getPaymentChannel())
                            .plaidAccount(plaidTransaction.getAccountId())
                            .build();
                }
                transactionRepository.save(transaction);
            }
            return "The transactions and the accounts are fetched successfully";
        }

        return "Error has occurred";
    }
}