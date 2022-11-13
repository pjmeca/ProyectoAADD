package aadd.persistencia.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.ItemPedido;

public class PedidoDTO implements Serializable{

		private Integer cliente;
		private Integer restaurante;
	    private String comentarios;
	    private String datosDireccion;
	    private Double importe;
	    
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
	    
	    
}
