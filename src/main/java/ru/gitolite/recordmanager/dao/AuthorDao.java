package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.Author;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class AuthorDao implements DaoInterface<Author> {
    @Override
    @Transactional
    public Optional<Author> findOneBy(String param, Object value) {
        Optional<Author> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cr = cb.createQuery(Author.class);
        Root<Author> root = cr.from(Author.class);
        cr.select(root).where(cb.equal(root.get(param), value));
        Query<?> query = session.createQuery(cr);
        try {
            Author result = (Author) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    private Optional<Author> findOneByFirstLastName(String[] values) {
        Optional<Author> res;

        if (values.length != 2) {
            return Optional.empty();
        }

        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cr = cb.createQuery(Author.class);
        Root<Author> root = cr.from(Author.class);
        cr.select(root).where(
                cb.equal(root.get("firstName"), values[0]),
                cb.equal(root.get("lastName"), values[1])
        );

        Query<?> query = session.createQuery(cr);
        try {
            Author result = (Author) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    public Optional<Author> findById(int id) {
        return this.findOneBy("id", id);
    }

    @Transactional
    public Optional<Author> findByName(String name) {
        String[] nameArr = name.split("\\s");
        return this.findOneByFirstLastName(nameArr);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Author> findAll() {
        return (List<Author>) getSessionFactory().openSession().createQuery("From Author").list();
    }

    @Override
    public void save(Author object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Author object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Author object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }
}
