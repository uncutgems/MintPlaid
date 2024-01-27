package com.application.mintplaid.plaid.balance;

import com.application.mintplaid.plaid.BaseResponse;
import com.application.mintplaid.plaid.PlaidAccountContract;
import com.application.mintplaid.plaid.PlaidItemContract;
import com.application.mintplaid.plaid.transaction.PlaidTransactionContract;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponse extends BaseResponse {
    @JsonProperty(value = "accounts", required = true)
    private List<PlaidAccountContract> accounts;

    @JsonProperty(value = "item", required = true)
    private PlaidItemContract item;

    @JsonProperty(value = "total_transactions", required = true)
    private Integer totalTransaction;

    @JsonProperty(value = "transactions", required = true)
    private List<PlaidTransactionContract> transactions;

}
