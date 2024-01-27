package com.application.mintplaid.service;

import com.application.mintplaid.entity.Transaction;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TransactionServiceInterface {
    List<Transaction> findTransactionByDate(String itemId, Date startDate, Date endDate);

    List<Transaction> getAllTransactionHistory(String itemId);

    String populateTransaction(String accessToken) throws URISyntaxException, ParseException;
}
