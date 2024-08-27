package com.recon.backend.models;

import lombok.Data;

import java.util.List;

@Data
public class CustomResponse {
    int status;
    String message;
    List<Bank> banks;
    List<Customer> customers;
    List<User> users;
    List<Batch> batches;
    List<BankTransaction> transactions;

}
