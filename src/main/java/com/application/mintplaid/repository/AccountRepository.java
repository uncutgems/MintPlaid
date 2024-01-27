package com.application.mintplaid.repository;

import com.application.mintplaid.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountByItem(String item);
    Account getAccountByPlaidAccountId(String plaidAccountId);

}