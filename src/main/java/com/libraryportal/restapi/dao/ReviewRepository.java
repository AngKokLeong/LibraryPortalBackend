package com.libraryportal.restapi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryportal.restapi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findBookReviewListByBookId(@RequestParam("bookId") Integer bookId, Pageable pageable);
    
    Review findBookReviewByUserEmailAndBookId(String userEmail, Integer bookId);

    @Modifying
    @Query("delete from Review where id in :book_id")
    void deleteAllByBookId(@Param("book_id") Integer book_id);
}
