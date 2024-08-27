package com.recon.backend.models;

import lombok.Data;

@Data
public class BankTransaction {
    private String transactionId;

    private String bookingDate;

    private String valueDate;

    private String remittanceInfo;

    private double reference;

    private double discount;

    private double amount;

    private boolean posted;

    private String creditDebitIndicator;

    private String batchId;

    private String bankId;


}
