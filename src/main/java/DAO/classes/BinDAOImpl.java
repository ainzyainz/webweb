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

    public BinDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Bin> getEntityClass() {
        return Bin.class;
    }
    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }


}
