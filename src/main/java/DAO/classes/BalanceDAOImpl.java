package DAO.classes;

import DAO.interfaces.BalanceDAO;
import entities.Balance;

import javax.persistence.EntityManager;

public class BalanceDAOImpl extends DAOImpl<Balance> implements BalanceDAO {

    public BalanceDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Balance> getEntityClass() {
        return Balance.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }


}
