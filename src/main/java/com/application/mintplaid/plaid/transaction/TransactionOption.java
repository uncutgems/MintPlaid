package com.application.mintplaid.plaid.transaction;

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
public class TransactionOption {
    @JsonProperty(value = "account_ids")
    private List<String> accountIds;

    @JsonProperty(value = "count")
    private Integer count;

    @JsonProperty(value = "offset")
    private Integer offset;
}
