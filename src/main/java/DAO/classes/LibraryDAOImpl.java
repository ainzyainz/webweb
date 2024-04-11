package DAO.classes;

import DAO.interfaces.BinDAO;
import DAO.interfaces.LibraryDAO;
import entities.Bin;
import entities.Library;

import javax.persistence.EntityManager;

public class LibraryDAOImpl extends DAOImpl<Library> implements LibraryDAO {

    @Override
    public Class<Library> getEntityClass() {
        return Library.class;
    }

    @Override
    public Library create(Library object) {
        return super.create(object);
    }

    @Override
    public Library read(long id) {
        return super.read(id);
    }

    @Override
    public Library update(long id, Library object) {
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


}
