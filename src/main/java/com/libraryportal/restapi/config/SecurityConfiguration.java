package com.libraryportal.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.okta.spring.boot.oauth.Okta;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //reference from https://developer.okta.com/blog/2017/12/18/spring-security-5-oidc
    //https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html

    private String jwkSetUri = "https://dev-63388380.okta.com/oauth2/default/v1/keys";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //disable cross site request forgery
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/books/**").permitAll());
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/reviews/**").permitAll());
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/checkouts/**").permitAll());
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/histories/**").permitAll());

        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/books/secure/**").authenticated())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())));

        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/reviews/secure/**").authenticated())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())));
        
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/messages/secure/**").authenticated())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())));

        
        //add CORS filters
        http.cors(withDefaults());

        //Add content to negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());
        
        //Force a non-empty response body for 401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }


}
