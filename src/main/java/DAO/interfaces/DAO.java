package DAO.interfaces;

import javax.persistence.EntityManager;

public interface DAO<T> {
    T create(T obj);
    T update(long id, T obj);
    T read(long id);
    boolean delete(long id);
    

}