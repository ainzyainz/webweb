package DAO.classes;

import DAO.interfaces.ReviewDAO;
import entities.Review;

import javax.persistence.EntityManager;

public class ReviewDAOImpl extends DAOImpl<Review>  implements ReviewDAO {

    public ReviewDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Review> getEntityClass() {
        return Review.class;
    }
}
