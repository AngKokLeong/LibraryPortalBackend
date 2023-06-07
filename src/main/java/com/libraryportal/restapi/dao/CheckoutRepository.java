package com.libraryportal.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libraryportal.restapi.entity.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer>{
    Checkout findBookCheckoutByUserEmailAndBookId(String userEmail, Integer bookId);
}
