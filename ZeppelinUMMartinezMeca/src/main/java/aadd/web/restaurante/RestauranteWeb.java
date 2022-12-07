package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteWeb implements Serializable{
    private Double latitudSelected;
    private Double longitudSelected;
    private String nombreRestaurante;
    private String calle;
    private String codigoPostal;
    private Integer numero;
    private String ciudad;
    private MapModel<Integer> simpleModel;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserSessionWeb usuarioSesion;
    private Integer responsableId;
    private ServicioGestionPlataforma servicio;
    private List<CategoriaRestaurante> categoriasSelected;

    public RestauranteWeb() {
        simpleModel = new DefaultMapModel<Integer>();
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    }
    
    @PostConstruct
    public void init() {
    	obtenerUsuarioSesion();
    	setRestaurantesMarker();
    }
    
    private void obtenerUsuarioSesion() {
        responsableId = usuarioSesion.getUsuario().getId();
    }
    
    private void setRestaurantesMarker() {
    	// Añadimos los restaurantes del usuario al mapa
        List<RestauranteDTO> restaurantes = RestauranteDAO.getRestauranteDAO().findRestaurantesByUsuarioResponsableId(responsableId);
        for(RestauranteDTO r : restaurantes) {
        	// Recuperamos las coordenadas del restaurante
        	Direccion d = DireccionDAO.getDireccionDAO().findByRestaurante(r.getId());
        	double longitud = d.getCoordenadas().getPosition().getValues().get(0);
        	double latitud = d.getCoordenadas().getPosition().getValues().get(1);
        	LatLng coord = new LatLng(latitud, longitud);
        	
        	simpleModel.addOverlay(new Marker<Integer>(coord, r.getNombre(), r.getId()));
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        latitudSelected = latlng.getLat();
        longitudSelected = latlng.getLng();
    }

    public void onMarkerRestauranteSelect(OverlaySelectEvent<Integer> event) {
        Marker<Integer> marker = (Marker<Integer>) event.getOverlay();
        Integer restauranteSelectedId = (Integer) marker.getData();
        try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/formPlatos.xhtml?id=" + restauranteSelectedId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearRestaurante() {
    	ArrayList<Integer> categoriasSelectedInt = new ArrayList<>();
		for(CategoriaRestaurante c : categoriasSelected)
			categoriasSelectedInt.add(c.getId());
		
        Integer restauranteId = servicio.registrarRestaurante(nombreRestaurante, responsableId, calle, codigoPostal,numero, ciudad, latitudSelected, longitudSelected, categoriasSelectedInt);

        if (restauranteId == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El restaurante no se ha podido crear", ""));
        } else {
            LatLng coord = new LatLng(latitudSelected, longitudSelected);
            simpleModel.addOverlay(new Marker<Integer>(coord, nombreRestaurante, restauranteId));
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurante creado correctamente", ""));
        }
    }
	public Double getLatitudSelected() {
		return latitudSelected;
	}
	public Double getLongitudSelected() {
		return longitudSelected;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public MapModel<Integer> getSimpleModel() {
		return simpleModel;
	}
	public void setSimpleModel(MapModel<Integer> simpleModel) {
		this.simpleModel = simpleModel;
	}
	public List<CategoriaRestaurante> getCategorias() {
		return servicio.getAllCategorias();
	}
	public List<CategoriaRestaurante> getCategoriasSelected() {
		return categoriasSelected;
	}
	public void setCategoriasSelected(List<CategoriaRestaurante> categoriasSelected) {
		this.categoriasSelected = categoriasSelected;
	}
	
}
