package com.guia.Guia_api.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

public class Token {

    public static Authentication decodeToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(token);
                String decodedString = new String(decodedBytes);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> tokenMap = mapper.readValue(decodedString, Map.class);

                String usuario = (String) tokenMap.get("usuario");
                long expiracao = (long) tokenMap.get("expiracao");

                if ("lucas".equals(usuario) && expiracao > Instant.now().toEpochMilli()) {
                    return new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}



