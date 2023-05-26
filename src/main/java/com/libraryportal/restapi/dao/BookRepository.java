package com.libraryportal.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryportal.restapi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
    
}
