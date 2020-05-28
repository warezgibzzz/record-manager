package ru.gitolite.recordmanager.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.gitolite.recordmanager.model.*;

public class DatabaseSessionFactory {
    private static SessionFactory sessionFactory;

    private DatabaseSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();

                configuration.addAnnotatedClass(Author.class);
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Category.class);
                configuration.addAnnotatedClass(Country.class);
                configuration.addAnnotatedClass(Tag.class);
                configuration.addAnnotatedClass(University.class);
                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Database exception!" + e);
            }
        }
        return sessionFactory;
    }
}
