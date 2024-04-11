package DAO.classes;

import DAO.interfaces.DAO;
import utils.functionalinterface.MyInterfaceToDAO;
import utils.functionalinterface.UtilsInterface;
import utils.hibernate.HibernateUtils;
import utils.reflectionutils.ReflectionUtils;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;


public abstract class DAOImpl<T> implements DAO<T> {

    private final EntityManager entityManager = HibernateUtils.getEntityManager();

    final Logger LOGGER = Logger.getLogger(DAOImpl.class.getName());

    public abstract Class<T> getEntityClass();

    public T create(T object) {
        if (object == null) {
            LOGGER.log(Level.INFO, CREATE_FAILED_MSG);
            return null;
        }
        MyInterfaceToDAO<T> betweenBeginAndCommitted = () -> {
            entityManager.persist(object);
            return object;
        };
        UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        LOGGER.log(Level.INFO, CREATE_SUCCESS_MSG);
        return object;
    }

    public T read(long id) {
        MyInterfaceToDAO<T> betweenBeginAndCommitted = () -> entityManager.find(getEntityClass(), id);
        T object = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        if (object == null) {
            LOGGER.log(Level.INFO,READ_FAILED_MSG);
        }
        LOGGER.log(Level.INFO,READ_SUCCESS_MSG);
        return object;
    }

    public T update(long id, T object) {
        MyInterfaceToDAO<T> betweenBeginAndCommitted = () -> {
            T temp = entityManager.find(getEntityClass(), id);
            return ReflectionUtils.updateReflection(object, temp, entityManager);
        };
        T result = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);

        if(result == null) {
            LOGGER.log(Level.INFO, UPDATE_FAILED_MSG);
        }
        LOGGER.log(Level.INFO, UPDATE_SUCCESS_MSG);
        return result;
    }

    public int delete(long id) {
        MyInterfaceToDAO<T> betweenBeginAndCommitted = () -> {
            T object = entityManager.find(getEntityClass(), id);
            entityManager.remove(object);
            return object;
        };
        T object = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        if (object == null) {
            LOGGER.log(Level.INFO, DELETE_FAILED_MSG);
            return -1;
        }
        LOGGER.log(Level.INFO, DELETE_SUCCESS_MSG);
        return 1;

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}

