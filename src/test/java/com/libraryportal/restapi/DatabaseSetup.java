package com.libraryportal.restapi;

import org.junit.jupiter.api.BeforeAll;

import com.libraryportal.restapi.data_loader.PostgreSQLJDBC;

public class DatabaseSetup {
    @BeforeAll
    void setupDatabase(){
        PostgreSQLJDBC.execute();
    }
    
}
