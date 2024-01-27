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
public class PlaidAccountContract {
    @JsonProperty(value = "account_id", required = true)
    private String accountId;

    @JsonProperty(value = "balances", required = true)
    private PlaidBalanceContract balances;

    @JsonProperty(value = "mask", required = true)
    private String mask;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "official_name", required = true)
    private String officialName;

    @JsonProperty(value = "subtype", required = true)
    private String subtype;

    @JsonProperty(value = "type", required = true)
    private String type;
}

