package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("gestionarIncidenciasWeb")
@ViewScoped
public class GestionarIncidenciasWeb implements Serializable{
	
	private ServicioGestionPlataforma servicio;
	
	private List<IncidenciaDTO> incidencias;
	private IncidenciaDTO incidenciaSeleccionada;
	private Integer idIncidencia;
	private String comentario;
	
	@Inject
    private FacesContext facesContext;
	@Inject
	private UserSessionWeb sesionWeb;
	
	public GestionarIncidenciasWeb() {
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    }
	
	@PostConstruct
	public void init() {
		if(sesionWeb.isLogin() && !sesionWeb.isRestaurante()) {
			try {
	            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
	            facesContext.getExternalContext().redirect(contextoURL);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		assert(sesionWeb.isRestaurante());
        UsuarioDTO u  = sesionWeb.getUsuario();
        incidencias = servicio.findIncidenciasAbiertasByRestaurante(u.getId());
	}

	public void onRowSelect(SelectEvent<RestauranteDTO> event) {
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/cerrarIncidencia.xhtml?idIncidencia="+incidenciaSeleccionada.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}

	public IncidenciaDTO getIncidenciaSeleccionada() {
		return incidenciaSeleccionada;
	}

	public void setIncidenciaSeleccionada(IncidenciaDTO incidenciaSeleccionada) {
		this.incidenciaSeleccionada = incidenciaSeleccionada;
	}

	public Integer getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(Integer idIncidencia) {
		this.idIncidencia = idIncidencia;
		incidenciaSeleccionada = servicio.getIncidenciaById(idIncidencia);
	}
	
	public String getNombreRestaurante(IncidenciaDTO incidencia) {
		RestauranteDTO r = servicio.getRestaurante(incidencia.getIdRestaurante());
		if(r != null)
			return r.getNombre();
		return "";
	}

	public String getDescripcion() {
		return incidenciaSeleccionada.getDescripcion();
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void cerrarIncidencia() {
		servicio.cerrarIncidencia(idIncidencia, comentario);
		
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/gestionarIncidencias.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
