package com.libraryportal.restapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryportal.restapi.dao.BookRepository;
import com.libraryportal.restapi.entity.Book;
import com.libraryportal.restapi.requestmodels.AddBookRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService {
    private BookRepository bookRepository;
    
    @Autowired
    public AdminService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public void postBook(AddBookRequest addBookRequest){
        Book book = new Book();
        
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setImg(addBookRequest.getImg());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        
        bookRepository.save(book);

    }

    public void increaseBookQuantity(Integer bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()){
            throw new Exception("Book not found");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);

        bookRepository.save(book.get());
    }

    public void decreaseBookQuantity(Integer bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0){
            throw new Exception("Book not found or Book quantity is zero");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);

        bookRepository.save(book.get());
    }
}
