package com.libraryportal.restapi.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libraryportal.restapi.dao.BookRepository;
import com.libraryportal.restapi.dao.CheckoutRepository;
import com.libraryportal.restapi.entity.Book;
import com.libraryportal.restapi.entity.Checkout;

@Service
@Transactional
public class BookService {
    
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository){
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook(String userEmail, Integer bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findBookCheckoutByUserEmailAndBookId(userEmail, bookId);
        
        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0){
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
            userEmail, 
            LocalDate.now().toString(), 
            LocalDate.now().plusDays(7).toString(), 
            bookId
        );

        checkoutRepository.save(checkout);

        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, Integer bookId){
        Checkout validateCheckout = checkoutRepository.findBookCheckoutByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout != null){
            return true;
        }else{
            return false;
        }
    }

    public int currentLoansCount(String userEmail){
        return checkoutRepository.findBookCheckoutByUserEmail(userEmail).size();
    }
}
