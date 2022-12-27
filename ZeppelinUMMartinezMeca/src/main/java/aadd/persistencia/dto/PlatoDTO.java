package aadd.persistencia.dto;

import java.io.Serializable;

public class PlatoDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
	protected Integer id;
    protected String descripcion;
    protected String titulo;
    protected Double precio;
    protected Boolean disponibilidad;
    
    public PlatoDTO(Integer id, String descripcion, String titulo, Double precio, Boolean disponibilidad) {
        super();
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
	
}
