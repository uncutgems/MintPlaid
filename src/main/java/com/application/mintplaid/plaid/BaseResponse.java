package com.application.mintplaid.plaid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    @JsonProperty(value = "request_id", required = true)
    public String requestId;
}
