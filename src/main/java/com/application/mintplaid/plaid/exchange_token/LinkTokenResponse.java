package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkTokenResponse {

    @JsonProperty(value = "link_token")
    private String linkToken;
    @JsonProperty(value = "expiration")
    private String expiration;
    @JsonProperty(value = "request_id")
    private String requestId;

}
