package com.application.mintplaid.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaidTransactionRequest {
    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    @JsonProperty(value = "client_id", required = true)
    private String clientId;

    @JsonProperty(value = "secret", required = true)
    private String secret;

    @JsonProperty(value = "start_date", required = true)
    private String startDate;

    @JsonProperty(value = "end_date", required = true)
    private String endDate;

    @JsonProperty(value = "options")
    private TransactionOption transactionOption;

}
