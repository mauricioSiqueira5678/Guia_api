package com.guia.Guia_api.Controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guia.Guia_api.Modelo.ModeloGuiaAprovado;
import com.guia.Guia_api.servico.ServicoAprovado;


@RestController
@CrossOrigin(origins = "*")
public class ControleAprovado {

    @Autowired
    private ServicoAprovado servico;

    @GetMapping("/listarTodosAprovados")
    public Iterable<ModeloGuiaAprovado> listarTodos() {
        System.out.println("Entrou na rota");    
        return servico.listarTodos();
    }
    
    @GetMapping("/listarAprovadosPorCategoria")
    public ResponseEntity<List<ModeloGuiaAprovado>> listarPorCategoria(@RequestParam String categoria) {
        List<ModeloGuiaAprovado> listaFiltrada = servico.listarPorCategoria(categoria);
        return ResponseEntity.ok(listaFiltrada);
    }

    @GetMapping("/pesquisarPorNome")
    public ResponseEntity<List<ModeloGuiaAprovado>> pesquisarPorNome(@RequestParam String nome) {
        List<ModeloGuiaAprovado> resultados = servico.listarPorNome(nome);
        return ResponseEntity.ok(resultados);
    }
}
