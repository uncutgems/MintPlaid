package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeTokenRequestContract {

    @JsonProperty(value = "client_id", required = true)
    private String clientId;

    @JsonProperty(value = "secret", required = true)
    private String secret;

    @JsonProperty(value = "public_token", required = true)
    private String publicToken;
}
