package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.mongodb.client.model.geojson.Point;

import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.Usuario;

public class Pedido implements Serializable{

	@BsonId
	private ObjectId id;
    private String codigo;
    private Integer cliente;
    private Integer restaurante;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaEsperado;
    private String comentarios;
    private String datosDireccion;
    private Double importe;
    private Integer repartidor;
    private List<EstadoPedido> estados;
    private List<ItemPedido> items;
    
    public Pedido() {
    	
    }
    
    public Pedido(String cod,Integer usu, Integer res, LocalDateTime fH, LocalDateTime fE, String coment, String datosDir
    		, Double importe, Integer rep, LocalDateTime fEstado, List<ItemPedido> items) {
    	if(items.isEmpty())throw new IllegalArgumentException("La lista de items esta vacia");
    	this.codigo=cod;
    	this.cliente=usu;
    	restaurante=res;
    	fechaHora=fH;
    	fechaEsperado=fE;
    	comentarios=coment;
    	datosDireccion=datosDir;
    	this.importe=importe;
    	repartidor=rep;
    	estados= new ArrayList<EstadoPedido>();
    	estados.add(new EstadoPedido(TipoEstado.INICIO,fEstado));
    	items=new ArrayList<>(items);
    }
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String cod) {
		this.codigo = cod;
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
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public LocalDateTime getFechaEsperado() {
		return fechaEsperado;
	}
	public void setFechaEsperado(LocalDateTime fechaEsperado) {
		this.fechaEsperado = fechaEsperado;
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
	public Integer getRepartidor() {
		return repartidor;
	}
	public void setRepartidor(Integer repartidor) {
		this.repartidor = repartidor;
	}
	
	public List<EstadoPedido> getEstados() {
		return new ArrayList<>(estados);
	}
	public void setEstados(List<EstadoPedido> estados) {
		this.estados = estados;
	}
	public List<ItemPedido> getItems() {
		return items;
	}
	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}
    
    
}
