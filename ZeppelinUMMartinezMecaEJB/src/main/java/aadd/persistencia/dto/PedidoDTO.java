package aadd.persistencia.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import aadd.persistencia.mongo.bean.EstadoPedido;

public class PedidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private Integer cliente;
	private Integer restaurante;
	private String comentarios;
	private String datosDireccion;
	private Double importe;
	private List<EstadoPedido> estados;

	public PedidoDTO(String codigo, Integer cliente, Integer restaurante, String comentarios, String datosDireccion,
			Double importe, List<EstadoPedido> estados) {
		this.codigo = codigo;
		this.cliente = cliente;
		this.restaurante = restaurante;
		this.comentarios = comentarios;
		this.datosDireccion = datosDireccion;
		this.importe = importe;
		this.estados = estados;
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
		return estados.get(estados.size() - 1).getEstado();
	}

	public boolean isUltimoEstado(String estado) {
		return getUltimoEstado().equals(estado);
	}

	public List<String> getSiguientesEstados() {
		List<String> estados = new ArrayList<>();

		String estadoActual = getUltimoEstado();
		if (estadoActual.equals("INICIO")) {
			estados.add("ACEPTADO");
			estados.add("CANCELADO");
		} else if (estadoActual.equals("ACEPTADO"))
			estados.add("PREPARADO");
		else if (estadoActual.equals("PREPARADO"))
			estados.add("RECOGIDO");

		return estados;
	}

	public void setEstado(String estado) {
		List<String> siguienteEstado = getSiguientesEstados();

		boolean valido = false;
		for (String e : siguienteEstado) {
			if (e.equals(estado))
				valido = true;
		}

		if (!valido)
			throw new IllegalStateException("No es posible cambiar del estado actual al estado: " + estado);
	}

	public void addEstado(EstadoPedido estado) {
		estados.add(estado);
	}
}
