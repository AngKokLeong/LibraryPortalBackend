package com.libraryportal.restapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libraryportal.restapi.entity.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer>{
    Checkout findBookCheckoutByUserEmailAndBookId(String userEmail, Integer bookId);
    List<Checkout> findBookCheckoutByUserEmail(String userEmail);

    @Modifying
    @Query("delete from Checkout where id in :book_id")
    void deleteAllByBookId(@Param("book_id") Integer bookId);
}
