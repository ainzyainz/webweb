package DAO.classes;


import DAO.interfaces.GameRequirementsDAO;
import entities.GameRequirements;
import entities.User;
import entities.UserDescription;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class GameRequirementsDAOImpl extends DAOImpl<GameRequirements> implements GameRequirementsDAO {

    @Override
    public Class<GameRequirements> getEntityClass() {
        return GameRequirements.class;
    }

    @Override
    public GameRequirements create(GameRequirements obj) { return super.create(obj); }

    @Override
    public GameRequirements read(long id) {
        return super.read(id);
    }

    @Override
    public GameRequirements update(long id, GameRequirements student) {return super.update(id, student); }

    @Override
    public int delete(long id) {
        return super.delete(id);
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
