package aadd.persistencia.jpa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.Usuario;

@Singleton(name = "RestauranteDAO")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class RestauranteDAO{
    
    public Integer count(int idResponsable) {
    	try {
            final String queryString = "SELECT count(r) FROM Restaurante r WHERE r.responsable.id=:responsable";
            Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
            query.setParameter("responsable", idResponsable);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            int valor = Integer.parseInt(query.getResultList().get(0).toString());
            //return (int) query.getResultList().get(0);
            return valor;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List<RestauranteDTO> transformarToDTO(List<Restaurante> restaurantes) {
        List<RestauranteDTO> rs = new ArrayList<RestauranteDTO>();
        for (Restaurante r : restaurantes) {
            rs.add(new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(), r.getNumPlatos()));
        }
        return rs;
    }
    
    public List<RestauranteDTO> findAllRestaurantes() {
	    try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Restaurante.findAllRestaurantes");
	        return transformarToDTO(query.getResultList());
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}
    
    public List<RestauranteDTO> findRestaurantesByUsuarioResponsable(int idResponsable) {
    	try {
            final String queryString = "SELECT r FROM Restaurante r WHERE r.responsable.id=:responsable";
            Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
            query.setParameter("responsable", idResponsable);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            return transformarToDTO(query.getResultList());
        } catch (RuntimeException re) {
            throw re;
        }
	}
}