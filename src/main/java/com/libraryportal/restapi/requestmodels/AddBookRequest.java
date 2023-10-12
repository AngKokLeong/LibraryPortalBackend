package com.libraryportal.restapi.requestmodels;

import lombok.Data;

@Data
public class AddBookRequest {
    
    private String title;
    private String description;
    private String author;
    private int copies;
    private String img;
    
}
