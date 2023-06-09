package com.libraryportal.restapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libraryportal.restapi.entity.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer>{
    Checkout findBookCheckoutByUserEmailAndBookId(String userEmail, Integer bookId);
    List<Checkout> findBookCheckoutByUserEmail(String userEmail);
}
