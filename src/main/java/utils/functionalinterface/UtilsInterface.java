package utils.functionalinterface;

import utils.hibernate.HibernateUtils;

import javax.persistence.EntityManager;

public class UtilsInterface {
    public static <T> T superMethodInterface(MyInterfaceToDAO<T> method, EntityManager entityManager) {
        entityManager = HibernateUtils.getEntityManager();
        entityManager.getTransaction().begin();
        T result = null;
        try{
            result = method.betweenBeginAndCommitted();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        entityManager.getTransaction().commit();
        return result;
    }
}
