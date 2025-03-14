package com.skillzy.skillzywebapp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public BCryptPasswordEncoder getPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
