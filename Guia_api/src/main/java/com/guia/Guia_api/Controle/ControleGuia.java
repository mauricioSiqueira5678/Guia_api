package com.guia.Guia_api.Controle;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guia.Guia_api.Modelo.ModeloGuia;
import com.guia.Guia_api.Modelo.ModeloMensagem;
import com.guia.Guia_api.servico.ServicoGuia;



@RestController
@CrossOrigin(origins = "*")
public class ControleGuia {

    @Autowired
    private ServicoGuia servico;

    public ControleGuia(ServicoGuia servico) {
        this.servico = servico;
    }

    @GetMapping("/listarTodos")
    public Iterable<ModeloGuia> listarTodos() {
        return servico.listarTodos();
    }

    @GetMapping("/")
    public String rota() {
        return "Api de produto funcionando";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(
            @RequestParam String nome,
            @RequestParam String tel,
            @RequestParam String face,
            @RequestParam String insta,
            @RequestParam String categoria,
            @RequestParam String descricao,
            @RequestParam MultipartFile imagem) throws IOException {
        // Cria um novo objeto GuiaModelo e preenche com os dados recebidos
        ModeloGuia guiaModelo = new ModeloGuia();
        guiaModelo.setNome(nome);
        guiaModelo.setTel(tel);
        guiaModelo.setFace(face);
        guiaModelo.setInsta(insta);
        guiaModelo.setCategoria(categoria);
        guiaModelo.setDescricao(descricao);
        guiaModelo.setImagem(imagem.getBytes()); // Converte a imagem para um array de bytes

        // Chama o serviço para cadastrar o guia
        return servico.cadastrarAlterar(null, guiaModelo, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody ModeloGuia guiaModelo) {
        return servico.cadastrarAlterar(null, guiaModelo, "alterar");
    }

    @PostMapping("/aprovarCadastroPorNome")
    public ResponseEntity<ModeloMensagem> aprovarCadastroPorNome(@RequestBody Map<String, String> payload) {
        return servico.aprovarCadastroPorNome(payload);
    }

    @DeleteMapping("/deletarCadastro/{id}")
    public ResponseEntity<ModeloMensagem> deletarCadastro(@PathVariable long id) {
        return servico.deletarCadastro(id);
    }
}

