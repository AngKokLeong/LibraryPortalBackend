package com.libraryportal.restapi.service;

import java.time.LocalDate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libraryportal.restapi.dao.BookRepository;
import com.libraryportal.restapi.dao.ReviewRepository;
import com.libraryportal.restapi.entity.Review;
import com.libraryportal.restapi.requestmodels.ReviewRequest;

@Service 
@Transactional
public class ReviewService {
    
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository){
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
        Review validateReview = reviewRepository.findBookReviewByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

        if(validateReview != null){
            throw new Exception("Review already created");
        }

        Review review = new Review();

        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);

        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(Object::toString).orElse(null));
        }
        
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }
}
