package com.libraryportal.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libraryportal.restapi.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    
    Payment findByUserEmail(String userEmail);

}