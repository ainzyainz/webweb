package utils.functionalinterface;

import javax.persistence.EntityManager;

public class UtilsInterface {

    public static <T> T superMethodInterface(MyInterfaceToDAO<T> method, EntityManager entityManager) {
        entityManager.getTransaction().begin();
        T result = method.betweenBeginAndCommitted();
        entityManager.getTransaction().commit();
        return result;
    }
}
