package aadd.persistencia.dto;

import java.io.Serializable;
import java.util.Date;

public class IncidenciaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date fechaCreacion;
	private String descripcion;
	private Date fechaCierre;
	private String comentarioCierre;
	
	public IncidenciaDTO(Integer id, Date fechaCreacion, String descripcion, Date fechaCierre,
			String comentarioCierre) {
		super();
		this.id = id;
		this.fechaCreacion = fechaCreacion;
		this.descripcion = descripcion;
		this.fechaCierre = fechaCierre;
		this.comentarioCierre = comentarioCierre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getComentarioCierre() {
		return comentarioCierre;
	}

	public void setComentarioCierre(String comentarioCierre) {
		this.comentarioCierre = comentarioCierre;
	}
}
