package DAO.interfaces;

import entities.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    boolean checkBalance(double userBalance, double price);
    List<User> findBySearch(String search);
    List<User> getUserByEmailAndPassword(String email, String password);
}
