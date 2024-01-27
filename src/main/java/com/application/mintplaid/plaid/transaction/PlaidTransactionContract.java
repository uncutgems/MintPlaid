package com.application.mintplaid.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaidTransactionContract {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("account_owner")
    private String accountOwner;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("authorized_date")
    private String authorizedDate;

    @JsonProperty("authorized_datetime")
    private String authorizedDatetime;

    @JsonProperty("category")
    private List<String> categories;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("check_number")
    private String checkNumber;

    @JsonProperty("date")
    private String date;

    @JsonProperty("datetime")
    private String dateTime;

    @JsonProperty("iso_currency_code")
    private String isoCurrencyCode;

    @JsonProperty("location")
    private PlaidLocationContract location;

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("payment_channel")
    private String paymentChannel;

    @JsonProperty("payment_meta")
    private PlaidPaymentMetaContract paymentMeta;

    @JsonProperty("pending")
    private Boolean pending;

    @JsonProperty("pending_transaction_id")
    private String pendingTransactionId;

    @JsonProperty("personal_finance_category")
    private String personalFinanceCategory;

    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("unofficial_currency_code")
    private String unofficialCurrencyCode;


}
