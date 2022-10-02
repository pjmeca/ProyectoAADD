package aadd.persistencia.dto;

import java.io.Serializable;

public class RestauranteDTO implements Serializable{
    protected Integer id;
    protected String nombre;
    protected Double valoracionGlobal;  

    public RestauranteDTO(Integer id, String nombre, Double valoracionGlobal) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.valoracionGlobal = valoracionGlobal;
    }
}