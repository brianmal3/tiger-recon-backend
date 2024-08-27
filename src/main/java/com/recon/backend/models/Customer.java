package com.recon.backend.models;

import lombok.Data;

@Data
public class Customer {
    private String customerId;
    private String name;
    private String apiKey;
    private String userName;
    private String password;
    private String clientId;
    private String clientSecret;
    private String dateRegistered;

}
