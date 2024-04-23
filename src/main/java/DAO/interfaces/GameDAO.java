package DAO.interfaces;

import entities.Game;

import java.util.List;
import java.util.Set;

public interface GameDAO extends DAO<Game> {
Set<Game> getLimited(int x, int y);
Game getGameByName(String name);
List<Game> findBySearch(String search);
List<Game> getAllGames();
}
