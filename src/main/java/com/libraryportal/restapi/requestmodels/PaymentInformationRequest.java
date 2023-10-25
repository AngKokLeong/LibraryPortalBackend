package com.libraryportal.restapi.requestmodels;

import lombok.Data;

@Data
public class PaymentInformationRequest {
    private double amount;
    private String currency;
    private String receiptEmail;
}
