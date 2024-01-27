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
public class PlaidErrorResponseContract {
    @JsonProperty(value = "error_type", required = true)
    private String errorType;

    @JsonProperty(value = "error_code", required = true)
    private String errorCode;

    @JsonProperty(value = "error_message", required = true)
    private String errorMessage;

    @JsonProperty(value = "display_message", required = true)
    private String displayMessage;

    @JsonProperty(value = "suggested_action", required = true)
    private String suggestedAction;
}
