package com.guia.Guia_api.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(MySecurityConfig.class);

    @PostConstruct
    public void init() {
        logger.info("MySecurityConfig initialized successfully.");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain...");
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/listarTodos").permitAll()
                .requestMatchers(HttpMethod.POST, "/aprovarCadastroPorNome").permitAll()
                .requestMatchers(HttpMethod.GET, "/listarTodosAprovados").permitAll()
                .requestMatchers(HttpMethod.GET, "/listarAprovadosPorCategoria").permitAll()
                .requestMatchers(HttpMethod.GET, "/pesquisarPorNome").permitAll()
                .requestMatchers(HttpMethod.POST, "/cadastrar").permitAll()
               

                .anyRequest().authenticated().and().cors();

        http.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);

        logger.info("Security filter chain configured.");
        return http.build();
    }
}


