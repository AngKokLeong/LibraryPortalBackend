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

    public PaymentIntent createPaymentIntent(PaymentInformationRequest paymentInformationRequest) throws StripeException{
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();

        params.put("amount", paymentInformationRequest.getAmount());
        params.put("currency", paymentInformationRequest.getCurrency());
        params.put("payment_method", paymentMethodTypes);

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
