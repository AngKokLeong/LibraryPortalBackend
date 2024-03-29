package com.libraryportal.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.libraryportal.restapi.entity.Book;
import com.libraryportal.restapi.entity.Message;
import com.libraryportal.restapi.entity.Review;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    
    private String theAllowedOrigins = "https://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
                                                    
        HttpMethod[] theUnsupportedActions = {
            HttpMethod.POST, 
            HttpMethod.PATCH,
            HttpMethod.DELETE,
            HttpMethod.PUT
        };

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);


        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);
        disableHttpMethods(Message.class, config, theUnsupportedActions);

        cors.addMapping(config.getBasePath() + "/**")
            .allowedOrigins(theAllowedOrigins);


    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions){
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));            
    }
}
