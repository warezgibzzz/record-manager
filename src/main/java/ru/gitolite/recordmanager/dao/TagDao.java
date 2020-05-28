package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.Tag;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class TagDao implements DaoInterface<Tag> {
    @Override
    @Transactional
    public Optional<Tag> findOneBy(String param, Object value) {
        Optional<Tag> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tag> cr = cb.createQuery(Tag.class);
        Root<Tag> root = cr.from(Tag.class);
        cr.select(root).where(cb.equal(root.get(param), value));

        Query<?> query = session.createQuery(cr);
        try {
            Tag result = (Tag) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    public Optional<Tag> findById(int id) {
        return this.findOneBy("id", id);
    }

    @Transactional
    public Optional<Tag> findByName(String name) {
        return this.findOneBy("name", name);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Tag> findAll() {
        return (List<Tag>) getSessionFactory().openSession().createQuery("From Tag").list();
    }

    @Override
    public void save(Tag object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Tag object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Tag object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }
}
