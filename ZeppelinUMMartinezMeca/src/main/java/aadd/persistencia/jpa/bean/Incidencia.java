package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Incidencia
 *
 */
@Entity
@Table(name="incidencia")
@NamedQueries({
    @NamedQuery(name = "Incidencia.findIncidenciasByRestaurante", query = " SELECT i FROM Incidencia i WHERE i.restaurante.id = :restaurante "),
    @NamedQuery(name = "Incidencia.findIncidenciasByUsuario", query = " SELECT i FROM Incidencia i WHERE i.usuario.id = :usuario "),
    @NamedQuery(name = "Incidencia.findIncidenciasByFechaCierre", query = " SELECT i FROM Incidencia i WHERE i.fechaCierre = :fechaCierre "),
    @NamedQuery(name = "Incidencia.findIncidenciasAbiertas", query = " SELECT i FROM Incidencia i WHERE i.fechaCierre is NULL "),
    @NamedQuery(name = "Incidencia.findIncidenciasAbiertasByRestaurante", query = " SELECT i FROM Incidencia i WHERE i.restaurante.id = :restaurante AND i.fechaCierre is NULL")
})
public class Incidencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="id")
	private Integer id;
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="fechaCierre")
	private Date fechaCierre;
	@Column(name="comentarioCierre")
	private String comentarioCierre;
	@Column(name="pedido")
	private String codPedido;
	
	@ManyToOne
	@JoinColumn(name="restaurante")
	private Restaurante restaurante;
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;

	public Incidencia() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getComentarioCierre() {
		return comentarioCierre;
	}

	public void setComentarioCierre(String comentarioCierre) {
		this.comentarioCierre = comentarioCierre;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public String getIdPedido() {
		return codPedido;
	}

	public void setIdPedido(String codPedido) {
		this.codPedido = codPedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
   
}
