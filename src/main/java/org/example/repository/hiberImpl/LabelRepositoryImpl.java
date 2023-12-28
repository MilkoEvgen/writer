package org.example.repository.hiberImpl;

import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public Label create(Label label) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(label);
            session.flush();
            label = session.get(Label.class, label.getId());
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
        }
        return label;
    }

    @Override
    public Label getById(Integer integer) {
        Label label;
        try (Session session = HibernateUtil.getSession()) {
            label = session.get(Label.class, integer);
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels;
        try (Session session = HibernateUtil.getSession()) {
            String HQL = "FROM Label";
            Query<Label> query = session.createQuery(HQL, Label.class);
            labels = query.getResultList();
        }
        return labels;
    }

    @Override
    public Label update(Label label) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.update(label);
            session.flush();
            label = session.get(Label.class, label.getId());
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
        }
        return label;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Label label = session.get(Label.class, integer);
            session.remove(label);
            transaction .commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        return true;
    }

    @Override
    public boolean existsById(Integer integer) {
        if (integer == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        try (Session session = HibernateUtil.getSession()) {
            Label label = session.get(Label.class, integer);
            if (label == null){
                return false;
            }
        }
        return true;
    }
}
