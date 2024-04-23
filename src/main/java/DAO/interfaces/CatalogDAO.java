package DAO.interfaces;

import entities.Catalog;

import java.util.List;
import java.util.Set;

public interface CatalogDAO extends DAO<Catalog> {
    List<Catalog> getAllCatalogs();
}
