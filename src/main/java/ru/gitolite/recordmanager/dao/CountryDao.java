package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.Country;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class CountryDao implements DaoInterface<Country> {
    @Override
    @Transactional
    public Optional<Country> findOneBy(String param, Object value) {
        Optional<Country> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> cr = cb.createQuery(Country.class);
        Root<Country> root = cr.from(Country.class);
        cr.select(root).where(cb.equal(root.get(param), value));

        Query<?> query = session.createQuery(cr);
        try {
            Country result = (Country) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    public Optional<Country> findById(int id) {
        return this.findOneBy("id", id);
    }

    @Transactional
    public Optional<Country> findByName(String name) {
        return this.findOneBy("name", name);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Country> findAll() {
        return (List<Country>) getSessionFactory().openSession().createQuery("From Country").list();
    }

    @Override
    public void save(Country object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Country object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Country object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }
}
