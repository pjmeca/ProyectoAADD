package aadd.persistencia.jpa.dao;

import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;

public class CategoriaRestauranteDAO extends ExtensionDAO<CategoriaRestaurante>{
	
	public CategoriaRestauranteDAO(Class<CategoriaRestaurante> persistedClass) {
        super(persistedClass);
    }

    private static CategoriaRestauranteDAO categoriaRestauranteDAO;

    public static CategoriaRestauranteDAO getCategoriaRestauranteDAO() {
        if (categoriaRestauranteDAO == null)
        	categoriaRestauranteDAO = new CategoriaRestauranteDAO(CategoriaRestaurante.class);
        return categoriaRestauranteDAO;
    }
    
    public List<CategoriaRestaurante> findAll() {
	    try {
	        return EntityManagerHelper.getEntityManager().createNamedQuery("CategoriaRestaurante.findAll").getResultList();
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}
    
    public CategoriaRestaurante getById(Integer id) {
	    try {
	    	 Query query = EntityManagerHelper.getEntityManager().createNamedQuery("CategoriaRestaurante.findById");
	    	 query.setParameter("id", id);
	    	 return (CategoriaRestaurante) query.getResultList().get(0);
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}

}
