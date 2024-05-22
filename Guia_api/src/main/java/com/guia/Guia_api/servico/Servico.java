package com.guia.Guia_api.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.guia.Guia_api.Modelo.ModeloLogin;
import com.guia.Guia_api.repositorio.RepositorioLogin;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Servico {

    @Autowired
    private RepositorioLogin repositorioLogin;

    private static final String USUARIO = "lucas";
    private static final String SENHA = "123";
    private static final long TEMPO_EXPIRACAO_MS = 600000; // 10 minutos

    public ResponseEntity<?> autenticarUsuario(String usuario, String senha) {
        ModeloLogin usuarioEncontrado = repositorioLogin.findByUsuario(usuario);
        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            String token = gerarToken(usuario);
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Usuário ou senha inválidos"));
        }
    }

    private String gerarToken(String usuario) {
        try {
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("usuario", usuario);
            tokenMap.put("expiracao", Instant.now().plusMillis(TEMPO_EXPIRACAO_MS).toEpochMilli());
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(tokenMap);
            return Base64.getEncoder().encodeToString(jsonString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }
}




