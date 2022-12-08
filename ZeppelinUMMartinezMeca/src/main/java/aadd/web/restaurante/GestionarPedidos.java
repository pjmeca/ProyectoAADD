package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.persistencia.mongo.dao.PedidoDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;

@Named
@ViewScoped
public class GestionarPedidos implements Serializable {

	private List<PedidoDTO> pedidos;
	
	@Inject
	private UserSessionWeb sesion;
	@Inject
    private FacesContext facesContext;
	
	public void init() {
		pedidos = PedidoDAO.getPedidoDAO().getByRestaurante(sesion.getUsuario().getId());
	}
	
	public void setEstado(PedidoDTO pedido, String estado) {
		pedido.setEstado(estado);
		pedido.addEstado(ServicioGestionPedido.getServicioGestionPedido().editarEstado(pedido.getCodigo(), TipoEstado.valueOf(estado), LocalDateTime.now()));
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
