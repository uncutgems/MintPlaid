package com.application.mintplaid.plaid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaidBalanceContract {
    @JsonProperty(value = "available", required = true)
    private Double available;

    @JsonProperty(value = "current", required = true)
    private Double current;

    @JsonProperty(value = "iso_currency_code", required = true)
    private String isoCurrencyCode;

    @JsonProperty(value = "limit", required = true)
    private Double limit;

    @JsonProperty(value = "unofficial_currency_code", required = true)
    private String unofficialCurrencyCode;
}
