package com.avitam.billing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
@Document(collection = "BillOfSupply")
@Getter
@Setter
@NoArgsConstructor
public class BillOfSupply extends BaseEntity{

    private String date;
    private int billNo;
    private String customerName;
    private double rate;
    private String particulars;
    private double kg;
    private int quantity;
    private double totalPrice;
    private double grossPrice;
    private String shopName;
    private String shopShortDescription;
    private String shopAddress;
    private String mobileNumber;
    private double upiPayment;
    private double cashPayment;
    private double pendingAmount;
}
