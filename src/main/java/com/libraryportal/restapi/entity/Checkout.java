package com.libraryportal.restapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {
    
    public Checkout(){}

    public Checkout(String userEmail, String checkoutDate, String returnDate, Integer bookId){
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.bookId = bookId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name="checkout_date")
    private String checkoutDate;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "book_id")
    private Integer bookId;

}
