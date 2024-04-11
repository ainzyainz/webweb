package DAO.classes;

import DAO.interfaces.ReviewDAO;
import entities.Review;

public class ReviewDAOImpl extends DAOImpl<Review>  implements ReviewDAO {

    @Override
    public Class<Review> getEntityClass() {
        return Review.class;
    }

    @Override
    public Review create(Review obj) {
        return super.create(obj);
    }

    @Override
    public Review update(long id, Review obj) {
        return super.update(id,obj);
    }

    @Override
    public Review read(long id) {
        return super.read(id);
    }

    @Override
    public int delete(long id) {
        return super.delete(id);
    }
}
