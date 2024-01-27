package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaidUserContract {
    @JsonProperty(value = "client_user_id", required = true)
    private String clientUserId;

    @JsonProperty(value = "legal_name")
    private String legalName;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "email_address")
    private String emailAddress;

    @JsonProperty(value = "date_of_birth")
    private String dateOfBirth;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "id_number")
    private String idNumber;

}
