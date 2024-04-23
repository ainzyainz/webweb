package utils.functionalinterface;

import utils.hibernate.HibernateUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class UtilsInterface {
    @Transactional(rollbackOn = Exception.class)
    public static <T> T superMethodInterface(MyInterfaceToDAO<T> method, EntityManager entityManager) {
        if (!entityManager.isOpen()){
            entityManager = HibernateUtils.getEntityManager();
        }
        entityManager.getTransaction().begin();
        T result = method.betweenBeginAndCommitted();
        entityManager.getTransaction().commit();
        return result;
    }
}
