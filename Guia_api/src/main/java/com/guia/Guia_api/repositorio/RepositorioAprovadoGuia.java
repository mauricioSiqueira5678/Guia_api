package com.guia.Guia_api.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.guia.Guia_api.Modelo.ModeloGuiaAprovado;

public interface RepositorioAprovadoGuia  extends CrudRepository<ModeloGuiaAprovado, Long> {
    List<ModeloGuiaAprovado> findByCategoria(String categoria);

    List<ModeloGuiaAprovado> findByNomeContainingIgnoreCase(String nome);
}
