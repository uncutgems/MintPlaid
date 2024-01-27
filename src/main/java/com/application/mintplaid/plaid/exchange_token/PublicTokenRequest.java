package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicTokenRequest {
    @JsonProperty(value = "public_token", required = true)
    private String publicToken;

    @JsonProperty(value = "secret", required = true)
    private String username;

}
