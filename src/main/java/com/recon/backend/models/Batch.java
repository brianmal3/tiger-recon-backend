package com.recon.backend.models;

import lombok.Data;

@Data
public class Batch {
    private String batchId;
    private String name;
    private String dateRegistered;

    private String batch_id;


    private String branch_code;


    private String batch_date;


    private String operator_name;


    private String sub_total;


    private double discount;


    private double total;


    private boolean posted;
}
