package com.application.mintplaid.service;

import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.Transaction;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface AccountServiceInterface {
    List<Account> getAllAccountDetails(String item);
}
