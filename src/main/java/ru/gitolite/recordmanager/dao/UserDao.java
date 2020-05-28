package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.User;
import ru.gitolite.recordmanager.service.StateManager;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class UserDao implements DaoInterface<User> {
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

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return (List<User>) getSessionFactory().openSession().createQuery("From User").list();
    }

    @Override
    public void save(User object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User object) {
        User currentUser = (User) StateManager.getState().get("user");
        if  (object.getId() == currentUser.getId()) {
            System.out.println("You cannot delete yourself");;
            return;
        }

        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }
}
