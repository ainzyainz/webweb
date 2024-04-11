package DAO.interfaces;

import entities.UserDescription;

import java.util.List;

public interface UserDescriptionDAO extends DAO<UserDescription> {
    List<UserDescription> findBySearch(String search);
}
