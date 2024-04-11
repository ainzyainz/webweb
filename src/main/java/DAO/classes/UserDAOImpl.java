package DAO.classes;

import DAO.interfaces.UserDAO;
import entities.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static utils.constant.ConstantsContainer.*;
import static utils.constant.ConstantsContainer.GET_ALL_ENTITY_SUCCESS;

public class UserDAOImpl extends DAOImpl<User> implements UserDAO {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User create(User object) {
        return super.create(object);
    }

    @Override
    public User read(long id) {
        return super.read(id);
    }

    @Override
    public User update(long id, User object) {
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

    public List<User> getAllUsers() {
        LOGGER.log(Level.INFO, START_GET_ALL_ENTITY);
        List<User> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_ALL_USER_QUERY, getEntityClass())
                    .getResultList();
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        LOGGER.log(Level.INFO, GET_ALL_ENTITY_SUCCESS);

        return list;
    }

    public User getUserByEmail(String email) {
        String query = String.format("%s%s%s", GET_USER_BY_EMAIL_QUERY, email, END_QUERY);
        List<User> users = getEntityManager().createNativeQuery(query, User.class).getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.stream().findFirst().orElse(null);
        }
    }


    public User getUserByPassword(String password) {
        String query = String.format("%s%s%s", GET_USER_BY_PASSWORD_QUERY, password, END_QUERY);
        List<User> users = getEntityManager().createNativeQuery(query, User.class).getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.stream().findFirst().orElse(null);
        }
    }

    @Override
    public List<User> findBySearch(String search) {
        List<User> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_SEARCH_USER, getEntityClass())
                    .setParameter(VALUE, search)
                    .getResultList();

        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        return list;
    }
    @Override
    public int checkBalance(double userBalance, double price){
        if (userBalance-price>=0){
            return 1;
        }
        return 0;
    }

    public List<User> getUserByEmailAndPassword(String email, String password) {
        String query = String.format("%s%s%s%s%s", GET_USER_BY_EMAIL_QUERY, email, AND_PASSWORD, password, END_QUERY2);
        List<User> users = getEntityManager().createNativeQuery(query, User.class).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users;
    }
}
