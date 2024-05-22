package com.guia.Guia_api.servico;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.guia.Guia_api.Modelo.ModeloGuiaAprovado;
import com.guia.Guia_api.Modelo.ModeloMensagem;
import com.guia.Guia_api.repositorio.RepositorioAprovadoGuia;


@Service
public class ServicoAprovado {

    @Autowired
    private RepositorioAprovadoGuia guiaAprovadoRepo;

    @Autowired
    private ModeloMensagem modeloMensagem;

    // Método para salvar um cadastro na tabela de aprovados
    public ModeloGuiaAprovado salvarAprovado(ModeloGuiaAprovado guiaAprovado) {
        return guiaAprovadoRepo.save(guiaAprovado);
    }

    //Metodo para listar todos os pendentes
    public Iterable<ModeloGuiaAprovado> listarTodos(){
        return guiaAprovadoRepo.findAll();
    }

    public List<ModeloGuiaAprovado> listarPorCategoria(String categoria){
        return guiaAprovadoRepo.findByCategoria(categoria);
    }
    
    public List<ModeloGuiaAprovado> listarPorNome(String nome){
        return guiaAprovadoRepo.findByNomeContainingIgnoreCase(nome);
    }

     //Metodo para remover cadastro
     public ResponseEntity<ModeloMensagem> deletarPorNome(String nome) {
        guiaAprovadoRepo.deleteByNome(nome);
        modeloMensagem.setMensagem("Cadastro removido com sucesso");
        return new ResponseEntity<>(modeloMensagem, HttpStatus.OK);
    }
}