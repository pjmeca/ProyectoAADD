package aadd.persistencia.jpa.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Incidencia;

public class IncidenciaDAO extends ExtensionDAO<Incidencia>{
	
	private IncidenciaDAO(Class<Incidencia> persistedClass) {
        super(persistedClass);
    }

    private static IncidenciaDAO incidenciaDAO;
    
    public static IncidenciaDAO getIncidenciaDAO() {
        if (incidenciaDAO == null)
        	incidenciaDAO = new IncidenciaDAO(Incidencia.class);
        return incidenciaDAO;
    }
    
    public List<IncidenciaDTO> transformarToDTO(List<Incidencia> incidencias) {
    	List<IncidenciaDTO> menu = new ArrayList<IncidenciaDTO>();
	    for (Incidencia i : incidencias) {
	        menu.add(new IncidenciaDTO(i.getId(), i.getFechaCreacion(), i.getDescripcion(), i.getFechaCierre(), i.getComentarioCierre()));	   
	    }
	    return menu;
	}
    
    public List<IncidenciaDTO> findIncidenciasByUsuario(Integer usuario){
    	try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidencia.findIncidenciasByUsuario");
	        query.setParameter("usuario", usuario);
	        return transformarToDTO(query.getResultList());
	    } catch (RuntimeException re) {
	        throw re;
	    }
    }
    
    public List<IncidenciaDTO> findIncidenciasByFechaCierre(Date fecha){
    	try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidencia.findIncidenciasByFechaCierre");
	        query.setParameter("fechaCierre", fecha);
	        return transformarToDTO(query.getResultList());
	    } catch (RuntimeException re) {
	        throw re;
	    }
    }
    
    public List<IncidenciaDTO> findIncidenciasSinCerrar(){
    	try {
	        Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidencia.findIncidenciasAbiertas");
	        return transformarToDTO(query.getResultList());
	    } catch (RuntimeException re) {
	        throw re;
	    }
    }
}
