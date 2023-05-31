package com.libraryportal.restapi.dao;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryportal.restapi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
    
    Page<Book> findBookByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    
}
