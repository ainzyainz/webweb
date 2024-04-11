package DAO.interfaces;

public interface DAO<T> {
    T create(T obj);
    T update(long id, T obj);
    T read(long id);
    int delete(long id);
    

}