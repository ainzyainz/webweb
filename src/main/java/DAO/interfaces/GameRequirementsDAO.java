package DAO.interfaces;

import entities.GameRequirements;

import java.util.List;

public interface GameRequirementsDAO extends DAO<GameRequirements> {
    List<GameRequirements> findBySearch(String search);
}
