package com.libraryportal.restapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.libraryportal.restapi.dao.PaymentRepository;
import com.stripe.Stripe;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {
    
    private PaymentRepository paymentRepository;


    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.secret.key}") String secretKey){
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    } 
}
