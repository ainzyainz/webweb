package DAO.classes;

import DAO.interfaces.BalanceDAO;
import DAO.interfaces.BinDAO;
import entities.Balance;
import entities.Bin;

import javax.persistence.EntityManager;

public class BalanceDAOImpl extends DAOImpl<Balance> implements BalanceDAO {

    @Override
    public Class<Balance> getEntityClass() {
        return Balance.class;
    }

    @Override
    public Balance create(Balance object) {
        return super.create(object);
    }

    @Override
    public Balance read(long id) {
        return super.read(id);
    }

    @Override
    public Balance update(long id, Balance object) {
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


}
