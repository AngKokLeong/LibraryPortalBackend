package com.libraryportal.restapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.libraryportal.restapi.dao.PaymentRepository;
import com.libraryportal.restapi.entity.Payment;
import com.libraryportal.restapi.requestmodels.PaymentInformationRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {
    
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.secret.key}") String secretKey){
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    } 

    private Long convertCurrencyToLong(double currencyAmount){
        return Double.valueOf(currencyAmount).longValue();
    }

    public PaymentIntent createPaymentIntent(PaymentInformationRequest paymentInformationRequest) throws StripeException{

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                                            .setAmount(convertCurrencyToLong(paymentInformationRequest.getAmount()))
                                            .setCurrency(paymentInformationRequest.getCurrency())
                                            .addPaymentMethodType("card")
                                            .build();

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null){
            throw new Exception("Payment information is missing");
        }

        payment.setAmount(0.00);
        paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
