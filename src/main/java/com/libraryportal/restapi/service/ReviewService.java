package com.libraryportal.restapi.service;

import java.time.LocalDate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libraryportal.restapi.dao.ReviewRepository;
import com.libraryportal.restapi.entity.Review;
import com.libraryportal.restapi.requestmodels.ReviewRequest;

@Service 
@Transactional
public class ReviewService {
    
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
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

    public Boolean userReviewListed(String userEmail, Integer bookId){
        Review validateReview = reviewRepository.findBookReviewByUserEmailAndBookId(userEmail, bookId);

        if (validateReview != null){
            return true;
        }

        return false;
    }
}
