package aadd.persistencia.jpa.dao;

import aadd.persistencia.jpa.bean.Plato;

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
}
