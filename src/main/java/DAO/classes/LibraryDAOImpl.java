package DAO.classes;

import DAO.interfaces.BinDAO;
import DAO.interfaces.LibraryDAO;
import entities.Bin;
import entities.Library;

import javax.persistence.EntityManager;

public class LibraryDAOImpl extends DAOImpl<Library> implements LibraryDAO {

    public LibraryDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Library> getEntityClass() {
        return Library.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }


}
