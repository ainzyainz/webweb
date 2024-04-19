package DAO.classes;


import DAO.interfaces.GameRequirementsDAO;
import entities.GameRequirements;
import entities.User;
import entities.UserDescription;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class GameRequirementsDAOImpl extends DAOImpl<GameRequirements> implements GameRequirementsDAO {

    public GameRequirementsDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<GameRequirements> getEntityClass() {
        return GameRequirements.class;
    }

    @Override
    public List<GameRequirements> findBySearch(String search) {
        List<GameRequirements> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_SEARCH_GAME_REQS   , getEntityClass())
                    .setParameter(VALUE, search)
                    .getResultList();
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        return list;
    }
}
