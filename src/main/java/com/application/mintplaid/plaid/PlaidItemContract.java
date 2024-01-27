package com.application.mintplaid.plaid;

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
public class PlaidItemContract {
    @JsonProperty(value = "available_products", required = true)
    private List<String> availableProducts;

    @JsonProperty(value = "billed_products", required = true)
    private List<String> billedProducts;

    @JsonProperty(value = "consent_expiration_time", required = true)
    private String consentExpirationTime;

    @JsonProperty(value = "error", required = true)
    private PlaidErrorResponseContract error;

    @JsonProperty(value = "institution_id", required = true)
    private String institutionId;

    @JsonProperty(value = "item_id", required = true)
    private String itemId;

    @JsonProperty(value = "optional_products", required = true)
    private List<String> optionalProducts;

    @JsonProperty(value = "products", required = true)
    private List<String> products;

    @JsonProperty(value = "update_type", required = true)
    private String updateType;

    @JsonProperty(value = "webhook", required = true)
    private String webhook;

}
