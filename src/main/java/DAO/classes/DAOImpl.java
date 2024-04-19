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

    private EntityManager entityManager;

    public DAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    final Logger LOGGER = Logger.getLogger(DAOImpl.class.getName());

    public abstract Class<T> getEntityClass();

    public T create(T object) {
        if (object == null) {
            LOGGER.log(Level.INFO, CREATE_FAILED_MSG);
            return null;
        }
        entityManager.persist(object);
        LOGGER.log(Level.INFO, CREATE_SUCCESS_MSG);
        return object;
    }

    public T read(long id) {
        T object = entityManager.find(getEntityClass(),id);
        if (object==null){
            LOGGER.log(Level.INFO,READ_FAILED_MSG+ id);
            return null;
        }
        LOGGER.log(Level.INFO,READ_SUCCESS_MSG);
        return object;
    }

    public T update(long id, T object) {
        T temp = entityManager.find(getEntityClass(),id);
        T result = ReflectionUtils.updateReflection(object,temp,entityManager);
        if (result==null){
            LOGGER.log(Level.INFO,UPDATE_FAILED_MSG);
            return null;
        }
        LOGGER.log(Level.INFO,UPDATE_SUCCESS_MSG);
        return result;
    }

    public boolean delete(long id) {
        T object = entityManager.find(getEntityClass(),id);
        if (object==null){
            LOGGER.log(Level.INFO,DELETE_FAILED_MSG + id);
            return false;
        }
        entityManager.remove(object);
        LOGGER.log(Level.INFO,DELETE_SUCCESS_MSG);
        return true;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}

