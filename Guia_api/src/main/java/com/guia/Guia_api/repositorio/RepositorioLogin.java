package com.guia.Guia_api.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.guia.Guia_api.Modelo.ModeloLogin;

public interface RepositorioLogin extends CrudRepository<ModeloLogin, Integer> {
    ModeloLogin findByUsuario(String usuario);
}
