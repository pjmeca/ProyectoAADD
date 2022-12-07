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
@NamedQueries({
    @NamedQuery(name = "CategoriaRestaurante.findAll", query = " SELECT c FROM CategoriaRestaurante c"),
    @NamedQuery(name = "CategoriaRestaurante.findById", query = " SELECT c FROM CategoriaRestaurante c WHERE c.id = :id ")
})
public class CategoriaRestaurante implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "categoria")
	private String categoria;
	
	
	@ManyToMany
	@JoinTable(
			name = "categoria_restaurante_restaurante",
			joinColumns = @JoinColumn(name = "categoria_restaurante"),
			inverseJoinColumns = @JoinColumn(name = "restaurante"))
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
	
	public void addRestaurante(Restaurante r) {
		restaurantes.add(r);
	}
	
	
	
   
}
