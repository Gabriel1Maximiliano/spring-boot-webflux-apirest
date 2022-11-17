package com.springboot.webflux.app.models.document;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

public class Categoria {
    @Id
    @NotBlank
    private String id;
    private String nombre;

    public Categoria() {
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
