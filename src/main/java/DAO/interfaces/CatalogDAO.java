package DAO.interfaces;

import entities.Catalog;

import java.util.Set;

public interface CatalogDAO extends DAO<Catalog> {
    Set<Catalog> findByName(String name);
}
