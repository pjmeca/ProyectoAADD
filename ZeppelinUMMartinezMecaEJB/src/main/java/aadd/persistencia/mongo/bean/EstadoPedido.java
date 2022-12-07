package aadd.persistencia.mongo.bean;

import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class EstadoPedido {
	@BsonId
	private ObjectId id;
	private LocalDateTime fechaEstado;
	private String estado;
	
	public EstadoPedido() {
		
	}
	
	public EstadoPedido(TipoEstado estado, LocalDateTime f) {
		this.estado=estado.toString();
		fechaEstado=f;
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public LocalDateTime getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(LocalDateTime fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
