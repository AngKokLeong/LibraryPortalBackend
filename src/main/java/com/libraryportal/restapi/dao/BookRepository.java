package com.libraryportal.restapi.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryportal.restapi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
    
    Page<Book> findBookByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    
    Page<Book> findBookByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query("select o from Book o where id in :book_ids")
    List<Book> findBooksByBookIds(@Param("book_ids") List<Integer> bookId);

}
