package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkTokenRequest {
    @JsonProperty(value = "client_id", required = true)
    private String clientId;

    @JsonProperty(value = "secret", required = true)
    private String secret;

    @JsonProperty(value = "client_name", required = true)
    private String clientName;

    @JsonProperty(value = "language", required = true)
    private String language;

    @JsonProperty(value = "country_codes", required = true)
    private List<String> countryCodes;

    @JsonProperty(value = "user", required = true)
    private PlaidUserContract plaidUserContract;

    @JsonProperty(value = "products")
    private List<String> products;

    @JsonProperty(value = "additional_consented_products")
    private List<String> additionalConsentedProducts;

    @JsonProperty(value = "webhook")
    private String webhook;

    @JsonProperty(value = "access_token")
    private String accessToken;


}
