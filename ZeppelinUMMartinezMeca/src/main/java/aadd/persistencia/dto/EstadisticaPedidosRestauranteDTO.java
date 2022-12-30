package aadd.persistencia.dto;

import java.io.Serializable;

public class EstadisticaPedidosRestauranteDTO implements Serializable{ 

	private static final long serialVersionUID = 1L;
	
	private String nombre;
    private Integer numPedidos;
    
    public EstadisticaPedidosRestauranteDTO(String nombre, Integer numPedidos) {
        super();
        this.nombre = nombre;
        this.numPedidos = numPedidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumPedidos() {
        return numPedidos;
    }

    public void setNumPedidos(Integer total) {
        this.numPedidos = total;
    }       
}