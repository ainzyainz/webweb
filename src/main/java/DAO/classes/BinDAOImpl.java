package DAO.classes;

import DAO.interfaces.BinDAO;
import DAO.interfaces.UserDAO;
import entities.Bin;
import entities.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static utils.constant.ConstantsContainer.*;

public class BinDAOImpl extends DAOImpl<Bin> implements BinDAO {

    @Override
    public Class<Bin> getEntityClass() {
        return Bin.class;
    }

    @Override
    public Bin create(Bin object) {
        return super.create(object);
    }

    @Override
    public Bin read(long id) {
        return super.read(id);
    }

    @Override
    public Bin update(long id, Bin object) {
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
