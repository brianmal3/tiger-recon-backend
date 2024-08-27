package com.recon.backend.models;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String name;
    private String customerId;
    private String email;
    private String cellphone;
    private String dateRegistered;
    private String firebaseUid;
    private UserType userType;
}




enum UserType {
    CUSTOMER_USER,
    ADMIN_USER
}
