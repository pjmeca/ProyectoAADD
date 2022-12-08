package aadd.persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;

public class PlatoDAO extends ExtensionDAO<Plato> {

    private PlatoDAO(Class<Plato> persistedClass) {
        super(persistedClass);
    }

    private static PlatoDAO platoDAO;

    public static PlatoDAO getPlatoDAO() {
        if (platoDAO == null)
            platoDAO = new PlatoDAO(Plato.class);
        return platoDAO;
    }
    
    public List<PlatoDTO> findPlatosDisponiblesByRestaurante(Integer restaurante) {
	    try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Plato.findPlatosDisponiblesByRestaurante");
	        query.setParameter("restaurante", restaurante);
	        return transformarToDTO(query.getResultList());
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}

	public List<PlatoDTO> transformarToDTO(List<Plato> platos) {
	    List<PlatoDTO> menu = new ArrayList<PlatoDTO>();
	    for (Plato p : platos) {
	        menu.add(new PlatoDTO(p.getId(), p.getDescripcion(), p.getTitulo(), p.getPrecio()));
	    }
	    return menu;
	}
	
	public PlatoDTO findPlatoById(Integer id) {
	    try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Plato.findPlatoById");
	        query.setParameter("id", id);
	        return (PlatoDTO) transformarToDTO(query.getResultList()).get(0);
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}
	
	public List<Restaurante> getRestaurantesByPlatosDisponibles(){
		try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Plato.getRestaurantesByPlatosDisponibles");
	        return query.getResultList();
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}
}
