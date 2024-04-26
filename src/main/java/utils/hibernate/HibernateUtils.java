package utils.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static javax.persistence.Persistence.createEntityManagerFactory;

public class HibernateUtils {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            createEntityManagerFactory("via");

    public static EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

    public static EntityManager getEntityManager() {
        if (!entityManager.isOpen()||entityManager==null){
            entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        }
        return entityManager;
    }
    public static void closeEntityManager(){
        ENTITY_MANAGER_FACTORY.createEntityManager().close();
    }
    public static void close() {
        ENTITY_MANAGER_FACTORY.close();
    }
}
