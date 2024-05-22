package com.guia.Guia_api.Modelo;

import org.hibernate.annotations.NotFound;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name= "pendentes")
@Getter
@Setter

public class ModeloGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @NotFound
    @NonNull
    @JsonIgnore

    private Long id;
    private String nome;
    private String tel;
    private String face;
    private String insta;
    private String categoria;
    private String descricao;
   
    @Lob
    private byte[] imagem; // Campo para armazenar a imagem
    
}
