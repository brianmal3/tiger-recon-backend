package com.recon.backend.models;

import lombok.Data;

@Data
public class Bank {
    private String bankId;
    private String name;
    private String apiKey;
    private String userName;
    private String password;
    private String clientId;
    private String clientSecret;
    private String dateRegistered;
    private String authUrl;
    private String baseUrl;
    private String tokenUrl;
}
