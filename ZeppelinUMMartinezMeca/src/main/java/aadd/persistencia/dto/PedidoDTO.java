package aadd.persistencia.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.zeppelinum.ServicioGestionPedido;

public class PedidoDTO implements Serializable{

		private String codigo;
		private Integer cliente;
		private Integer restaurante;
	    private String comentarios;
	    private String datosDireccion;
	    private Double importe;
	    private LocalDateTime fechaHora;
	    private List<EstadoPedido> estados;
	    
	    public PedidoDTO(String codigo, Integer cliente, Integer restaurante, String comentarios, String datosDireccion, Double importe, List<EstadoPedido> estados, LocalDateTime fechaHora) {
	    	this.codigo = codigo;
	    	this.cliente = cliente;
	    	this.restaurante = restaurante;
	    	this.comentarios = comentarios;
	    	this.datosDireccion = datosDireccion;
	    	this.importe = importe;
	    	this.estados = estados;
	    	this.fechaHora = fechaHora;
	    }
	    
		public Integer getCliente() {
			return cliente;
		}
		public void setCliente(Integer cliente) {
			this.cliente = cliente;
		}
		public Integer getRestaurante() {
			return restaurante;
		}
		public void setRestaurante(Integer restaurante) {
			this.restaurante = restaurante;
		}
		public String getComentarios() {
			return comentarios;
		}
		public void setComentarios(String comentarios) {
			this.comentarios = comentarios;
		}
		public String getDatosDireccion() {
			return datosDireccion;
		}
		public void setDatosDireccion(String datosDireccion) {
			this.datosDireccion = datosDireccion;
		}
		public Double getImporte() {
			return importe;
		}
		public void setImporte(Double importe) {
			this.importe = importe;
		}
		public List<EstadoPedido> getEstados() {
			return estados;
		}
		public void setEstados(List<EstadoPedido> estados) {
			this.estados = estados;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		
		public String getUltimoEstado() {
			return estados.get(estados.size()-1).getEstado();
		}
		
		public boolean isUltimoEstado(String estado) {
			return getUltimoEstado().equals(estado);
		}
		
		public LocalDateTime getFechaHora() {
			return fechaHora;
		}

		public void setFechaHora(LocalDateTime fechaHora) {
			this.fechaHora = fechaHora;
		}

		public List<String> getSiguientesEstados() {
			List<String> estados = new ArrayList<>();
			
			String estadoActual = getUltimoEstado();
			if(estadoActual.equals("INICIO")) {
				estados.add("ACEPTADO");
				estados.add("CANCELADO");
			}
			else if (estadoActual.equals("ACEPTADO"))
				estados.add("PREPARADO");
			else if (estadoActual.equals("PREPARADO"))
				estados.add("RECOGIDO");
			
			return estados; 
		}
		
		/*
		 * Comprueba si el estado es un estado siguiente válido.
		 */
		public boolean isEstadoValido(String estado) {
			List<String> siguienteEstado = getSiguientesEstados();
			
			boolean valido = false;
			for(String e : siguienteEstado) {
				if(e.equals(estado))
					valido = true;
			}
			
			return valido;
		}
		
		/*
		 * Cambia el estado del pedido, lanza una excepción si no es un estado válido.
		 */
		public void cambiarAEstado(EstadoPedido estado) {
			if(!isEstadoValido(estado.getEstado()))
				throw new IllegalStateException("No es posible cambiar del estado actual al estado: "+estado);
			
			estados.add(estado);
		}
}
