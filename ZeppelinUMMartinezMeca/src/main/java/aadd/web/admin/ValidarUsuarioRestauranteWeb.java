package aadd.web.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("validarRestaurante")
@ViewScoped
public class ValidarUsuarioRestauranteWeb implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ServicioGestionPlataforma servicio;
	
	private List<UsuarioDTO> usuarios;
	private List<UsuarioDTO> usuariosSeleccionados;
	
	@Inject
	private FacesContext facesContext;
	@Inject
	private UserSessionWeb userSessionWeb;
	
	@PostConstruct
	public void init() {
		usuarios = UsuarioDAO.getUsuarioDAO().findUsuariosNoValidadosAndTipoRestaurante();
		
		if(userSessionWeb.isLogin() && !userSessionWeb.isAdmin()) {
			try {
	            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
	            facesContext.getExternalContext().redirect(contextoURL);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public ValidarUsuarioRestauranteWeb() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}
	
	public void validar() {
		for(UsuarioDTO usuario : usuariosSeleccionados) {
			servicio.validarUsuario(usuario.getId());
		}
		usuarios = UsuarioDAO.getUsuarioDAO().findUsuariosNoValidadosAndTipoRestaurante();
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioDTO> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	public void setUsuariosSeleccionados(List<UsuarioDTO> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

}
