package DAO.classes;

import DAO.interfaces.GameDAO;
import entities.Game;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static utils.constant.ConstantsContainer.*;

public class GameDAOImpl extends DAOImpl<Game> implements GameDAO {

    @Override
    public Class<Game> getEntityClass() {
        return Game.class;
    }

    @Override
    public Game create(Game object) {
        return super.create(object);
    }

    @Override
    public Game read(long id) {
        return super.read(id);
    }

    @Override
    public Game update(long id, Game object) {
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

    public Set<Game> getLimited(int x, int y) {
        Query query = getEntityManager().createQuery(FROM_GAME, getEntityClass());
        query.setFirstResult(x);
        query.setMaxResults(y);
        List<Game> list = query.getResultList();
        Set<Game> games = new HashSet<>();
        if (query.getResultList().isEmpty()){
            LOGGER.log(Level.INFO,"No games found");
            return games;
        }
        list.forEach(games::add);
        LOGGER.log(Level.INFO,"Loaded page with games");
        return games;
    }

    public Game getGameByName(String name) {
        String query = String.format("%s%s%s", GET_GAME_BY_NAME_QUERY, name, END_QUERY);
        List<Game> games = getEntityManager()
                .createNativeQuery(query, Game.class)
                .getResultList();

        if (games.isEmpty()) {
            return null;
        } else {
            return games
                    .stream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public List<Game> findBySearch(String search) {
        List<Game> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_SEARCH_GAME, getEntityClass())
                    .setParameter(VALUE, search)
                    .getResultList();
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        return list;
    }

    public List<Game> getAllGames() {
        LOGGER.log(Level.INFO, START_GET_ALL_ENTITY);
        List<Game> list = new ArrayList<>();
        try {
            list = getEntityManager()
                    .createQuery(GET_ALL_GAMES_QUERY, getEntityClass())
                    .getResultList();

        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO, LIST_IS_EMPTY);
        }
        LOGGER.log(Level.INFO, GET_ALL_ENTITY_SUCCESS);

        return list;
    }
}
