package org.example.repository.hiberImpl;

import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.repository.PostRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public Post create(Post post) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw e;
            }
        }
        return post;
    }

    @Override
    public Post getById(Integer integer) {
        Post post;
        try (Session session = HibernateUtil.getSession()) {
            post = session.get(Post.class, integer);
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts;
        try (Session session = HibernateUtil.getSession()) {
            Query<Post> query = session.createQuery("FROM Post", Post.class);
            posts = query.getResultList();
        }
        return posts;
    }

    @Override
    public Post update(Post post) {
        Transaction transaction = null;
        Post oldPost;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            oldPost = session.get(Post.class, post.getId());
            oldPost.setUpdated(LocalDateTime.now());
            if (post.getContent() != null && !post.getContent().isEmpty()) {
                oldPost.setContent(post.getContent());
            }
            oldPost.setPostStatus(post.getPostStatus());
            session.update(oldPost);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        return oldPost;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Post post = session.get(Post.class, integer);
            post.setPostStatus(PostStatus.DELETED);
            session.update(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
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
            Post post = session.get(Post.class, integer);
            if (post == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Post> getAllByAuthorId(Integer authorId) {
        List<Post> posts;
        try (Session session = HibernateUtil.getSession()) {
            Query<Post> query = session.createQuery("FROM Post WHERE author.id = :authorId", Post.class);
            query.setParameter("authorId", authorId);
            posts = query.getResultList();
        }
        return posts;
    }
}
