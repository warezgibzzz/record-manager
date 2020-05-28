package ru.gitolite.recordmanager.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.gitolite.recordmanager.model.University;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.gitolite.recordmanager.service.DatabaseSessionFactory.getSessionFactory;


public class UniversityDao implements DaoInterface<University> {
    @Override
    @Transactional
    public Optional<University> findOneBy(String param, Object value) {
        Optional<University> res;
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<University> cr = cb.createQuery(University.class);
        Root<University> root = cr.from(University.class);
        cr.select(root).where(cb.equal(root.get(param), value));

        Query<?> query = session.createQuery(cr);
        try {
            University result = (University) query.getSingleResult();
            session.close();
            res = Optional.ofNullable(result);
        } catch (NoResultException e) {
            res = Optional.empty();
        }

        return res;
    }

    @Transactional
    public Optional<University> findById(int id) {
        return this.findOneBy("id", id);
    }

    @Transactional
    public Optional<University> findByName(String name) {
        return this.findOneBy("title", name);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<University> findAll() {
        return (List<University>) getSessionFactory().openSession().createQuery("From University").list();
    }

    @Override
    public void save(University object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(University object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(object);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(University object) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(object);
        tx1.commit();
        session.close();
    }

    @Override
    public String toString() {
        return "university" ;
    }
}
