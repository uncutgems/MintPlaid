package com.application.mintplaid.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Environment {
    // Sandbox Environment
    public static final String sandboxEnv = "https://sandbox.plaid.com";
    public static final String sandboxClientId = "";
    public static final String sandboxSecret = "";

    public static final String localDevelopment = "http://localhost:8080";

    // Production Environment
    public static final String developmentEnv = "https://";

    public static final String appName = "PlaidPersonal";
    public static final List<String> countries = Arrays.asList("US", "CA");
    public static final List<String> products = List.of("transactions");

}

