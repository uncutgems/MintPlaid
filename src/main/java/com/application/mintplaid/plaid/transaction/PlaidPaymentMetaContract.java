package com.application.mintplaid.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaidPaymentMetaContract {
    @JsonProperty("by_order_of")
    private String byOrderOf;

    @JsonProperty("payee")
    private String payee;

    @JsonProperty("payer")
    private String payer;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_processor")
    private String paymentProcessor;

    @JsonProperty("ppd_id")
    private String ppdId;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("reference_number")
    private String referenceNumber;
}
