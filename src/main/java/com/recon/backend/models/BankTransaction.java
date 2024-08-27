package com.recon.backend.models;

import lombok.Data;

@Data
public class BankTransaction {
    private String transaction_id;

    private String booking_date;

    private String value_date;

    private String remittance_info;

    private double reference;

    private double discount;

    private double amount;

    private boolean posted;

    private String  credit_debit_indicator;

    private String batch_id;


}
