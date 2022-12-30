package aadd.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("crearIncidenciaWeb")
@ViewScoped
public class CrearIncidenciaWeb implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ServicioGestionPlataforma servicio;
	
	private List<PedidoDTO> pedidos;
	private PedidoDTO pedidoSeleccionado;
	private String codigoPedido;
	private String descripcion;
	
	@Inject
    private FacesContext facesContext;
	
	@Inject
	private UserSessionWeb sesionWeb;
	
	public CrearIncidenciaWeb() {
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    }
	
	@PostConstruct
	public void init() {
		assert(sesionWeb.isCliente());
        UsuarioDTO u  = sesionWeb.getUsuario();
        pedidos = ServicioGestionPedido.getServicioGestionPedido().findPedidosByUsuario(u.getId());
	}

	public void onRowSelect(SelectEvent<RestauranteDTO> event) {
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/usuario/crearIncidencia.xhtml?codigoPedido="+pedidoSeleccionado.getCodigo());
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

	public PedidoDTO getPedidoSeleccionado() {
		return pedidoSeleccionado;
	}

	public void setPedidoSeleccionado(PedidoDTO pedidoSeleccionado) {
		this.pedidoSeleccionado = pedidoSeleccionado;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
		pedidoSeleccionado = ServicioGestionPedido.getServicioGestionPedido().findByCodigo(codigoPedido);
	}
	
	public String getNombreRestaurante() {
		return servicio.getRestaurante(pedidoSeleccionado.getRestaurante()).getNombre();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void registrarIncidencia() {
		servicio.registrarIncidencia(pedidoSeleccionado.getCliente(), pedidoSeleccionado.getRestaurante(), codigoPedido, descripcion);
		
		try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/usuario/incidencia.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
