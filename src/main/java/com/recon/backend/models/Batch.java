package com.recon.backend.models;

import lombok.Data;

@Data
public class Batch {
    private String batchId;

    private String bankId;

    private String name;

    private String dateRegistered;


    private String branchCode;


    private String batchDate;


    private String operatorName;


    private String subTotal;


    private double discount;


    private double total;


    private boolean posted;
}
