package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static service.DatabaseSessionFactory.getSessionFactory;


public class UserDao implements DaoInterface {
    @Override
    public Optional<User> findOneBy(String param, Object value) {
        Optional<User> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get(param), value));

        Query<?> query = session.createQuery(cr);
        try {
            User result = (User) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    public Optional<User> findById(int id) {
        return this.findOneBy("id", id);
    }

    public Optional<User> findByName(String name) {
        return this.findOneBy("name", name);
    }

    public void save(User user) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return (List<User>) getSessionFactory().openSession().createQuery("From User").list();
    }
}
