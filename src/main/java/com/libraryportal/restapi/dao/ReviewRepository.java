package com.libraryportal.restapi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryportal.restapi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findBookByBookId(@RequestParam("bookId") Integer bookId, Pageable pageable);
    
}
