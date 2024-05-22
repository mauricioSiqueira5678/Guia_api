package com.guia.Guia_api.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.guia.Guia_api.Modelo.ModeloLogin;
import com.guia.Guia_api.repositorio.RepositorioLogin;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class Servico {

    @Autowired
    private RepositorioLogin repositorioLogin;

    // Token fixo para autenticação
    private static final String USUARIO = "lucas";
    private static final String SENHA = "123";
    private static final long TEMPO_EXPIRACAO_MS = 600000; // 10 minutos

    public ResponseEntity<?> autenticarUsuario(String usuario, String senha) {
        ModeloLogin usuarioEncontrado = repositorioLogin.findByUsuario(usuario);
        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            // Se o usuário existir e a senha estiver correta, gera um token com tempo de expiração
            String token = gerarToken();
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } else {
            // Se o usuário não existir ou a senha estiver incorreta, retorna um erro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Usuário ou senha inválidos"));
        }
    }

    private String gerarToken() {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("usuario", USUARIO);
        tokenMap.put("expiracao", Instant.now().plusMillis(TEMPO_EXPIRACAO_MS).toEpochMilli());
        return tokenMap.toString(); // Aqui você pode ajustar a forma como o token é retornado
    }
}



