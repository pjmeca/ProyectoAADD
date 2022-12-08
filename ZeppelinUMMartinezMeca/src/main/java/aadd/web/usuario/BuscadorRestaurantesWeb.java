package aadd.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("buscadorRestaurantes")
@ViewScoped
public class BuscadorRestaurantesWeb implements Serializable{
	
	private ServicioGestionPlataforma servicio;
	
	private List<RestauranteDTO> restaurantes;
	private List<RestauranteDTO> restaurantesFiltrados;
	private RestauranteDTO restauranteSeleccionado;
	
	@Inject
    private FacesContext facesContext;
	
	public BuscadorRestaurantesWeb() {
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
        restaurantes = servicio.getRestaurantesDisponibles();
    }

	public void onRowSelect(SelectEvent<RestauranteDTO> event) {
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/usuario/pedido.xhtml?id=" + restauranteSeleccionado.getId());
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

		return restaurante.getNombre().toLowerCase().contains(filterText)
				|| Integer.toString(restaurante.getNumPlatos()).equals(filterText)
				|| Double.toString(restaurante.getValoracionGlobal()).equals(filterText);
	}
	
	public void setRestauranteSeleccionado(RestauranteDTO restaurante) {
		restauranteSeleccionado = restaurante;
	}
	public RestauranteDTO getRestauranteSeleccionado() {
		return restauranteSeleccionado;
	}
	public List<RestauranteDTO> getRestaurantes() {
		return restaurantes;
	}
	public List<RestauranteDTO> getRestaurantesFiltrados() {
		return restaurantesFiltrados;
	}

}
