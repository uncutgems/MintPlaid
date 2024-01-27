package com.application.mintplaid.service;

import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;

import java.net.URISyntaxException;

public interface PlaidServiceInterface {
    public LinkPlaidResponse initialLink(LinkPlaidRequest plaidRequest) throws URISyntaxException;
    public LinkPlaidResponse fixLink(LinkPlaidRequest plaidRequest) throws URISyntaxException;
}
