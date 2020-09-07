package com.example.helptest.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    private String secretKey = "securesecuresecuresecuresecuresecure";
    private Integer tokenExpirationAfterDays = 14;

    public JwtConfig() {
    }

    public JwtConfig(String secretKey, Integer tokenExpirationAfterDays) {
        this.secretKey = secretKey;
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }


    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}

