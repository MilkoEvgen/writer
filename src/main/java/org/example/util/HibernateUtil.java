package org.example.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        String configFile = "/hibernate.cfg.xml";
        try {
            Configuration configuration = new Configuration().configure(configFile);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            System.exit(1);
        }
        return null;
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}