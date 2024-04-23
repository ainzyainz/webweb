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

    public CatalogDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Catalog> getEntityClass() {
        return Catalog.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    @Override
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
