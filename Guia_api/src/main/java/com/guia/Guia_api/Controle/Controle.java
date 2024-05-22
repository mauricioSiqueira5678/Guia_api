package com.guia.Guia_api.Controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.guia.Guia_api.Modelo.ModeloLogin;
import com.guia.Guia_api.servico.Servico;

@RestController
@CrossOrigin(origins = "*")
public class Controle {

    @Autowired
    private Servico servico;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ModeloLogin dadosLogin) {
        return servico.autenticarUsuario(dadosLogin.getUsuario(), dadosLogin.getSenha());
    }
}


