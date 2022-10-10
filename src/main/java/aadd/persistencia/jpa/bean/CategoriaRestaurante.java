package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CategoriaRestaurante
 *
 */
@Entity
@Table(name = "categoria_restaurante")
public class CategoriaRestaurante implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "categoria")
	private String categoria;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
    private List<Restaurante> restaurantes;
	
	

	public CategoriaRestaurante() {
		super();
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}



	public void setRestaurantes(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}
	
	
   
}
