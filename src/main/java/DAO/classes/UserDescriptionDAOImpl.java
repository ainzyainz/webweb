package DAO.classes;


import DAO.interfaces.UserDescriptionDAO;
import entities.UserDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class UserDescriptionDAOImpl extends DAOImpl<UserDescription> implements UserDescriptionDAO {

    private final Logger LOGGER = Logger.getLogger(UserDescriptionDAOImpl.class.getName());
    private int noOfRecords;

    @Override
    public Class<UserDescription> getEntityClass() {
        return UserDescription.class;
    }

    @Override
    public UserDescription create(UserDescription obj) { return super.create(obj); }

    @Override
    public UserDescription read(long id) {
        return super.read(id);
    }

    @Override
    public UserDescription update(long id, UserDescription student) {return super.update(id, student); }

    @Override
    public int delete(long id) {
        return super.delete(id);
    }


    @Override
    public List<UserDescription> findBySearch(String search) {
        List<UserDescription> list = new ArrayList<>();
        try {
            list = getEntityManager()
                .createQuery(GET_SEARCH, getEntityClass())
                 .setParameter(VALUE, search)
                 .getResultList();
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        return list;
    }
}