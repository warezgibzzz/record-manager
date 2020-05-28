package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.Book;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class BookDao implements DaoInterface<Book> {
    @Override
    @Transactional
    public Optional<Book> findOneBy(String param, Object value) {
        Optional<Book> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cr = cb.createQuery(Book.class);
        Root<Book> root = cr.from(Book.class);
        cr.select(root).where(cb.equal(root.get(param), value));

        Query<?> query = session.createQuery(cr);
        try {
            Book result = (Book) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    public Optional<Book> findById(int id) {
        return this.findOneBy("id", id);
    }

    @Transactional
    public Optional<Book> findByName(String name) {
        return this.findOneBy("name", name);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Book> findAll() {
        return (List<Book>) getSessionFactory().openSession().createQuery("From Book").list();
    }

    @Override
    public void save(Book object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Book object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Book object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }

    @Override
    public String toString() {
        return "book" ;
    }
}
