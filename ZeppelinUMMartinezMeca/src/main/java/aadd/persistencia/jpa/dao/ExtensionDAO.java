package aadd.persistencia.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import aadd.persistencia.jpa.EntityManagerHelper;

public abstract class ExtensionDAO<T> implements DAO<T> {
    protected Class<T> persistedClass;
    protected String name;

    public ExtensionDAO(Class<T> persistedClass) {
        this.persistedClass = persistedClass;
        name = persistedClass.getName().substring(persistedClass.getName().lastIndexOf(".") + 1);
    }   
    
    @Override
    public T findById(Integer id) {
        try {
            T instance = EntityManagerHelper.getEntityManager().find(persistedClass, id);
            if (instance != null) {
                EntityManagerHelper.getEntityManager().refresh(instance);
            }
            return instance;
        } catch (RuntimeException re) {
            throw re;
        }
    }   
    @Override
    public List<T> findByIds(List<Integer> id) {
        return null;
        // TODO Auto-generated method stub
    }
    @Override
    public void save(T t, EntityManager em) {
        em.persist(t);   
    }
    
    public void update(T t, EntityManager em) {
    	em.merge(t);
    }

    @Override
    public List<T> getAll() {
    	try {
            final String queryString = " SELECT model from " + name + " model ";
            Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            return query.getResultList();
        } catch (RuntimeException re) {
            throw re;
        }
    }
}
