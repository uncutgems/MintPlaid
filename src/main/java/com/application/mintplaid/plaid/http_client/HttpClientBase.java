package com.application.mintplaid.plaid.http_client;

import org.springframework.http.MediaType;

import java.net.http.HttpClient;

public class HttpClientBase {
    protected final HttpClient client;
    protected final MediaType mediaType;

    public HttpClientBase(HttpClient client) {
        this.client = client;
        this.mediaType = MediaType.APPLICATION_JSON ;
    }

    public HttpClient getClient() {
        return client;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
