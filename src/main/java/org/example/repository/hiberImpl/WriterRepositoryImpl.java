package org.example.repository.hiberImpl;

import org.example.model.Writer;
import org.example.repository.WriterRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer create(Writer writer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(writer);
            session.flush();
            writer = session.get(Writer.class, writer.getId());
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
        }
        return writer;
    }

    @Override
    public Writer getById(Integer integer) {
        Writer writer;
        try (Session session = HibernateUtil.getSession()) {
            writer = session.get(Writer.class, integer);
            Hibernate.initialize(writer.getPosts());
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers;
        try (Session session = HibernateUtil.getSession()) {
            String HQL = "FROM Writer";
            Query<Writer> query = session.createQuery(HQL, Writer.class);
            writers = query.getResultList();
        }
        return writers;
    }

    @Override
    public Writer update(Writer writer) {
        Transaction transaction = null;
        Writer oldWriter;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            oldWriter = session.get(Writer.class, writer.getId());
            if (writer.getFirstName() != null){
                oldWriter.setFirstName(writer.getFirstName());
            }
            if (writer.getLastName() != null){
                oldWriter.setLastName(writer.getLastName());
            }
            session.update(oldWriter);
            session.flush();
            writer = session.get(Writer.class, writer.getId());
            Hibernate.initialize(writer.getPosts());
            transaction .commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        return writer;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Writer writer = session.get(Writer.class, integer);
            session.remove(writer);
            transaction.commit();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean existsById(Integer integer) {
        if (integer == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        try (Session session = HibernateUtil.getSession()) {
            Writer writer = session.get(Writer.class, integer);
            if (writer == null){
                return false;
            }
        }
        return true;
    }
}
