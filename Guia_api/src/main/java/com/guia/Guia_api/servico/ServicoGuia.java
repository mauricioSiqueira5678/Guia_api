package com.guia.Guia_api.servico;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.guia.Guia_api.Modelo.ModeloGuia;
import com.guia.Guia_api.Modelo.ModeloGuiaAprovado;
import com.guia.Guia_api.Modelo.ModeloMensagem;
import com.guia.Guia_api.repositorio.RepositorioGuia;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
@Service
public class ServicoGuia {

    @Autowired
    private RepositorioGuia guiaRepo;

    @Autowired
    private ServicoAprovado guiaAprovadoServico;

    @Autowired
    private ModeloMensagem resposta;

    //Metodo para listar todos os pendentes
    public Iterable <ModeloGuia> listarTodos(){
        return guiaRepo.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(MultipartFile imagem, ModeloGuia guiaModelo, String acao) {
    if (guiaModelo.getNome().isEmpty() || guiaModelo.getTel().isEmpty() || guiaModelo.getDescricao().isEmpty()) {
        resposta.setMensagem("Preencha os campos obrigatórios");
        return new ResponseEntity<ModeloMensagem>(resposta, HttpStatus.BAD_REQUEST);
    } else {
        try {
            // Verifica se uma imagem foi enviada
            if (imagem != null && !imagem.isEmpty()) {
                // Se sim, converte a imagem para um array de bytes e salva no objeto guiaModelo
                guiaModelo.setImagem(imagem.getBytes());
            }
            // Salva ou atualiza o cadastro no banco de dados
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<ModeloGuia>(guiaRepo.save(guiaModelo), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<ModeloGuia>(guiaRepo.save(guiaModelo), HttpStatus.OK);
            }
        } catch (IOException e) {
            // Caso ocorra algum erro ao processar a imagem
            resposta.setMensagem("Erro ao processar a imagem: " + e.getMessage());
            return new ResponseEntity<ModeloMensagem>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    
    //Metodo para remover cadastro
    public ResponseEntity<ModeloMensagem> deletarCadastro(long id){
        guiaRepo.deleteById(id);
        resposta.setMensagem("Cadastro removido com sucesso");
        return new ResponseEntity<ModeloMensagem> (resposta, HttpStatus.OK);
    }

   
    // Método para aprovar cadastro pelo nome
    public ResponseEntity<ModeloMensagem> aprovarCadastroPorNome(@RequestBody Map<String, String> payload) {
        String nome = payload.get("nome");
        List<ModeloGuia> guias = guiaRepo.findByNome(nome);
        if (!guias.isEmpty()) {
            ModeloGuia guiaPendente = guias.get(0); // assumindo que o primeiro resultado é o desejado

            // Criar uma instância de GuiaModeloAprovados com os mesmos dados
            ModeloGuiaAprovado guiaAprovado = new ModeloGuiaAprovado();
            guiaAprovado.setNome(guiaPendente.getNome());
                guiaAprovado.setTel(guiaPendente.getTel());
                guiaAprovado.setFace(guiaPendente.getFace());
                guiaAprovado.setInsta(guiaPendente.getInsta());
                guiaAprovado.setCategoria(guiaPendente.getCategoria());
                guiaAprovado.setDescricao(guiaPendente.getDescricao());
                guiaAprovado.setImagem(guiaPendente.getImagem());
            
            // Salvar na tabela de aprovados
            guiaAprovadoServico.salvarAprovado(guiaAprovado);

            // Remover o cadastro da tabela de pendentes
            guiaRepo.deleteById(guiaPendente.getId());

            resposta.setMensagem("Cadastro aprovado e movido para a lista de aprovados");
            return new ResponseEntity<>(resposta, HttpStatus.OK);
        } else {
            resposta.setMensagem("Cadastro não encontrado pelo nome");
            return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
        }
    }

    public List<ModeloGuia> listarPorNome(String nome){
        return guiaRepo.findByNomeContainingIgnoreCase(nome);
    }

 
}
