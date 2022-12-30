package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("editarRestaurante")
@ViewScoped
public class EditarRestauranteWeb implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ServicioGestionPlataforma servicio;
	
	private List<RestauranteDTO> restaurantes;
	private List<RestauranteDTO> restaurantesFiltrados;
	private RestauranteDTO restauranteSeleccionado;
	private int idRestauranteSeleccionado;
	private List<CategoriaRestaurante> categoriasSeleccionadas;
	
	@Inject
    private FacesContext facesContext;
	@Inject
	private UserSessionWeb userSessionWeb;
	
	@PostConstruct
	public void init() {
		if(userSessionWeb.isLogin() && !userSessionWeb.isRestaurante()) {
			try {
	            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
	            facesContext.getExternalContext().redirect(contextoURL);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public EditarRestauranteWeb() {
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
        restaurantes = servicio.getRestaurantesDisponibles();
    }

	public void onRowSelect(SelectEvent<RestauranteDTO> event) {
		try {
			String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/editar.xhtml?restauranteSeleccionado="+restauranteSeleccionado.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {

		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText.isBlank()) {
			return true;
		} 

		RestauranteDTO restaurante = (RestauranteDTO) value;

		return restaurante.getNombre().toLowerCase().contains(filterText);
	}
	
	public void setRestauranteSeleccionado(RestauranteDTO restaurante) {
		System.out.println("S:"+restaurante);
		restauranteSeleccionado = restaurante;
	}
	public void setRestauranteSeleccionado(int id) {
		setRestauranteSeleccionado(RestauranteDAO.getRestauranteDAO().findByIdDTO(id));
		
	}
	public RestauranteDTO getRestauranteSeleccionado() {
		System.out.println("R:"+restauranteSeleccionado);
		return restauranteSeleccionado;
	}
	public List<RestauranteDTO> getRestaurantes() {
		return restaurantes;
	}
	public List<RestauranteDTO> getRestaurantesFiltrados() {
		return restaurantesFiltrados;
	}
	
	public int getIdRestauranteSeleccionado() {
		return idRestauranteSeleccionado;
	}

	public void setIdRestauranteSeleccionado(int idRestauranteSeleccionado) {
		this.idRestauranteSeleccionado = idRestauranteSeleccionado;
	}

	public String getNombreSeleccionado() {
		if(restauranteSeleccionado == null)
			setRestauranteSeleccionado(idRestauranteSeleccionado);
		
		return restauranteSeleccionado.getNombre();
	}
	
	public List<CategoriaRestaurante> getNoCategorias(){
		return servicio.getNoCategorias(idRestauranteSeleccionado);
	}

	public List<CategoriaRestaurante> getCategoriasSeleccionadas() {
		return categoriasSeleccionadas;
	}

	public void setCategoriasSeleccionadas(List<CategoriaRestaurante> categoriasSeleccionadas) {
		this.categoriasSeleccionadas = categoriasSeleccionadas;
	}
	
	public void continuar() {
		// Actualizar las categorías
		servicio.addCategorias(idRestauranteSeleccionado, categoriasSeleccionadas);
		// Regresar a la página de selección de restaurantes
		try {
			String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/editarRestaurante.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
