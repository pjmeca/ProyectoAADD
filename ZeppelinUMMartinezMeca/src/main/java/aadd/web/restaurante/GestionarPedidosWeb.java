package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.persistencia.mongo.dao.PedidoDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;

@Named("gestionarPedidos")
@ViewScoped
public class GestionarPedidosWeb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<PedidoDTO> pedidos;
	
	@Inject
	private UserSessionWeb sesion;
	@Inject
    private FacesContext facesContext;
	
	@PostConstruct
	public void init() {
		if(sesion.isLogin() && !sesion.isRestaurante()) {
			try {
	            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
	            facesContext.getExternalContext().redirect(contextoURL);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		List<RestauranteDTO> restaurantes = RestauranteDAO.getRestauranteDAO().findRestaurantesByUsuarioResponsableId(sesion.getUsuario().getId());
		pedidos = new ArrayList<>();
		for(RestauranteDTO r : restaurantes)
			pedidos.addAll(PedidoDAO.getPedidoDAO().getByRestaurante(r.getId()));
	}
	
	public void setEstado(PedidoDTO pedido, String estado) {
		if(pedido.isEstadoValido(estado))
			pedido.cambiarAEstado(ServicioGestionPedido.getServicioGestionPedido().editarEstado(pedido.getCodigo(), TipoEstado.valueOf(estado), LocalDateTime.now()));
		
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/gestionarPedidos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public List<PedidoDTO> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoDTO> pedidos) {
		this.pedidos = pedidos;
	}
}
