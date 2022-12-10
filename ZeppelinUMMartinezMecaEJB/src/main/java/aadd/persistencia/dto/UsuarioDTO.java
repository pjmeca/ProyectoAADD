package aadd.persistencia.dto;

import java.io.Serializable;
import java.time.LocalDate;

import aadd.persistencia.jpa.bean.TipoUsuario;

public class UsuarioDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
	protected Integer id;
    protected String nombre;
    protected String apellidos;
    protected TipoUsuario tipo;
    protected LocalDate fechaNacimiento;
    protected String email;

    
    public UsuarioDTO(Integer id, String nombre, String apellidos, LocalDate fechaNacimiento, TipoUsuario tipo, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
        this.email = email;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public TipoUsuario getTipo() {
		return tipo;
	}


	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public boolean isTipo(TipoUsuario tipo) {
		return this.tipo.equals(tipo);
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}