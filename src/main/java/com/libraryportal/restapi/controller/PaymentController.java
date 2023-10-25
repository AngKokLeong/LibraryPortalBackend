package com.libraryportal.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryportal.restapi.requestmodels.PaymentInformationRequest;
import com.libraryportal.restapi.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {
    
    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInformationRequest paymentInformationRequest) throws StripeException{
        
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInformationRequest);
        
        String paymentIntentJsonString = paymentIntent.toJson();

        return new ResponseEntity<>(paymentIntentJsonString, HttpStatus.OK);
    }
}
