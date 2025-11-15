package com.chisimdi.student_service.configs;

import org.apache.http.protocol.HTTP;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class HttpConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf(Csrf->Csrf.disable()).authorizeHttpRequests(auth->auth.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
