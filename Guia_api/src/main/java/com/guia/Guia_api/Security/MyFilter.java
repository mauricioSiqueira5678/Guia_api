package com.guia.Guia_api.Security;
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Processing request: {}", request.getRequestURI());

        if (request.getHeader("Authorization") != null) {
            Authentication auth = Token.decodeToken(request);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.warn("Failed to decode token for request: {}", request.getRequestURI());
                ErroDTO erro = new ErroDTO(401, "Usuário não autorizado");
                response.setStatus(erro.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().println(mapper.writeValueAsString(erro));
                response.getWriter().flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
        logger.info("Request processed successfully: {}", request.getRequestURI());
    }
}

