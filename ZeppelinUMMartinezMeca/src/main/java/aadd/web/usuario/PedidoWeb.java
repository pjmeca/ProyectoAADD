package aadd.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@SessionScoped
public class PedidoWeb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
    private FacesContext facesContext;
    private Integer idRestaurante;
    private List<ItemPedido> items;
    private ServicioGestionPlataforma servicio;
    private RestauranteDTO restaurante;
    private String direccion;
    private LocalTime hora;
    private String comentario;
    
    @Inject
    private UserSessionWeb sesionWeb;

    public PedidoWeb() {
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    }
    public void loadMenu() {
        List<PlatoDTO> menu = servicio.getMenuByRestaurante(idRestaurante, true);
        items = new ArrayList<>();
        for(PlatoDTO plato : menu) {
        	ItemPedido i = new ItemPedido();
        	i.setPlato(plato.getId());
        	i.setCantidad(0);
        	items.add(i);
        }
    }
    
    public String formalizar() {
    	// Comprobar que al menos haya pedido un plato
    	boolean alMenosUno = false;
    	for(ItemPedido item : items) {
    		if(item.getCantidad() > 0)
    			alMenosUno = true;
    	}
    	if(!alMenosUno) {
    		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error items", "Por favor, introduce al menos un item en tu pedido."));
    		return "";
    	}
    	
    	// Redirigimos al usuario
    	try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/usuario/pedidoFormalizar.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return "pedidoFormalizar.xhtml";
    }
    
    public String finalizar() {    	   	
    	ServicioGestionPedido.getServicioGestionPedido().crearPedido(sesionWeb.getUsuario().getId(), restaurante.getId(), LocalDateTime.now(), hora.atDate(LocalDate.now()), comentario, direccion, getImporte(), items);
    	
    	// Redirigimos al usuario
    	try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/usuario/pedidoFinalizado.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return "pedidoFinalizado.xhtml";
	}
    
    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
        restaurante = servicio.getRestaurante(idRestaurante);
    }
    public Integer getidRestaurante() {
        return idRestaurante;
    }
    public List<ItemPedido> getPedido() {
        return items;
    }
	public Integer getIdRestaurante() {
		return idRestaurante;
	}
	public List<ItemPedido> getItems() {
		return items;
	}
	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}
	public List<ItemPedido> getItemsPedido(){
		ArrayList<ItemPedido> itemsPedido = new ArrayList<>();
		for(ItemPedido item : items) {
			if(item.getCantidad() > 0)
				itemsPedido.add(item);
		}
		return itemsPedido;
	}
	public RestauranteDTO getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(RestauranteDTO restaurante) {
		this.restaurante = restaurante;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public double getImporte() {
		double importe = 0.0;
		for(ItemPedido item : items) {
			importe += item.getPrecio()*item.getCantidad();
		}
		return importe;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}   