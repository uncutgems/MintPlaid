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
public class ExchangeTokenResponseContract {
    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    @JsonProperty(value = "item_id", required = true)
    private String itemId;

    @JsonProperty(value = "request_id", required = true)
    private String requestId;
}
