package com.guia.Guia_api.Security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.Collections;

public class Token {

    public static Authentication decodeToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if ("Bearer lucas:123".equals(authHeader)) {
            return new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
        } else {
            return null;
        }
    }
}

