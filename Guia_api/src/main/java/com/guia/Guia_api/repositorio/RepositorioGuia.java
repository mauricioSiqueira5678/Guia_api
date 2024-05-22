package com.guia.Guia_api.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guia.Guia_api.Modelo.ModeloGuia;

public interface RepositorioGuia extends JpaRepository<ModeloGuia, Long> {
    List<ModeloGuia> findByNome(String nome);

    List<ModeloGuia> findByNomeContainingIgnoreCase(String nome);
}
