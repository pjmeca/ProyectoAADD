package aadd.web.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("validarRestaurante")
@ViewScoped
public class ValidarUsuarioRestauranteWeb implements Serializable{
	
	private ServicioGestionPlataforma servicio;
	
	private List<UsuarioDTO> usuarios;
	private List<UsuarioDTO> usuariosSeleccionados;
	
	public ValidarUsuarioRestauranteWeb() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}
	
	public void init() {
		usuarios = UsuarioDAO.getUsuarioDAO().findUsuariosNoValidadosAndTipoRestaurante();
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
