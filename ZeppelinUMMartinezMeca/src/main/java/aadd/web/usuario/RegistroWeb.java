package aadd.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RegistroWeb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String apellidos;
	private String correo;
	private String clave;
	private String clave2;
	private LocalDate fechaNacimiento;
	private String tipo;
	@Inject
	protected FacesContext facesContext;
	@Inject
	private UserSessionWeb userSessionWeb;
	
	@PostConstruct
	public void init() {
		// Si ya ha iniciado sesión, es porque está registrando un rider, así que debe ser admin
		if(userSessionWeb.isLogin() && !userSessionWeb.isAdmin()) {
			try {
	            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
	            facesContext.getExternalContext().redirect(contextoURL);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}

	public void registro() {
		// Comprobar que los campos no están vacíos
		if(nombre.isBlank() || apellidos.isBlank() || fechaNacimiento == null || correo.isBlank() || clave.isBlank() || tipo.isBlank()) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Por favor, rellena todos los campos!"));
			return;
		}
		
		// Comprobar que las claves coinciden
		if(!clave.equals(clave2)) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Las claves no coinciden!"));
			return;
		}
		
		if (ServicioGestionPlataforma.getServicioGestionPlataforma().isUsuarioRegistrado(correo)) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un usuario con el email " + correo));
			return;
		}
		Integer idUser = ServicioGestionPlataforma.getServicioGestionPlataforma().registrarUsuario(nombre, apellidos,
				fechaNacimiento, correo, clave, TipoUsuario.valueOf(tipo));
		if (idUser != null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario registrado correctamente"));
		} else {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usurio no ha podido ser registrado"));
		}
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}	
}