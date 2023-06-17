package com.libraryportal.restapi.requestmodels;

import lombok.Data;
import java.util.Optional;

@Data
public class ReviewRequest{
    private double rating;
    private Integer bookId;
    private Optional<String> reviewDescription;
}