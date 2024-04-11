package DAO.classes;

import DAO.interfaces.CatalogDAO;
import entities.Catalog;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static utils.constant.ConstantsContainer.*;

public class CatalogDAOImpl extends DAOImpl<Catalog> implements CatalogDAO {

    @Override
    public Class<Catalog> getEntityClass() {
        return Catalog.class;
    }

    @Override
    public Catalog create(Catalog object) {
        return super.create(object);
    }

    @Override
    public Catalog read(long id) {
        return super.read(id);
    }

    @Override
    public Catalog update(long id, Catalog object) {
        return super.update(id, object);
    }

    @Override
    public int delete(long id) {
        return super.delete(id);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }


    @Override
    public Set<Catalog> findByName(String name) {
        //Set<Foo> foo = new HashSet<Foo>(myList);
        return new HashSet<>(getEntityManager().createQuery(FIND_BY_NAME, Catalog.class).setParameter("value", name).getResultList());
    }
    public List<Catalog> getAllCatalogs() {
        LOGGER.log(Level.INFO, START_GET_ALL_ENTITY);
        List<Catalog> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_ALL_CATALOG_QUERY, getEntityClass())
                    .getResultList();
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        LOGGER.log(Level.INFO, GET_ALL_ENTITY_SUCCESS);
        return list;
    }
}
