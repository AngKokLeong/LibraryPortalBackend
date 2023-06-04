package com.libraryportal.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libraryportal.restapi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
}
