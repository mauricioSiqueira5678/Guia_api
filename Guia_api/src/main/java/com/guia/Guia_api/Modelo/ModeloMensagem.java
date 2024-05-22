package com.guia.Guia_api.Modelo;

import org.springframework.stereotype.Component;

@Component
public class ModeloMensagem {
    
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

