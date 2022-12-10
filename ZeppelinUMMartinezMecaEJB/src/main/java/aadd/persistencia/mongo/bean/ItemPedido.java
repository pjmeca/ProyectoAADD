package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import aadd.persistencia.jpa.bean.Plato;

public class ItemPedido implements Serializable{
	@BsonId
	private ObjectId id;
	private Integer cantidad;
	private Integer plato;
	private Double precioTotal;
	
	public ItemPedido() {
		
	}
	
	public ItemPedido(Integer cant, Integer plat,Double precio) {
		cantidad=cant;
		plato=plat;
		precioTotal=precio;
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getPlato() {
		return plato;
	}
	public void setPlato(Integer plato) {
		this.plato = plato;
	}
	public Double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(Double predioTotal) {
		this.precioTotal = predioTotal;
	}
	
}
