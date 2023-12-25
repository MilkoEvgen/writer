package org.example.repository;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.util.DbUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public Post create(Post post) {
        String SQL = "INSERT INTO posts (content, created, author_id, status_id) VALUES(?, ?, ?, ?)";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getContent());
            statement.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
            statement.setInt(3, post.getAuthorId());
            statement.setInt(4, 2);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int postId = generatedKeys.getInt(1);
                    post = getById(postId);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public Post getById(Integer id) {
        Post post = null;
        String SQL = "SELECT * FROM posts WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                post = getPostFromResultSet(resultSet);
                post.setLabels(getLabelsByPostId(connection, post.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String SQL = "SELECT * FROM posts";
        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Post post = getPostFromResultSet(resultSet);
                post.setLabels(getLabelsByPostId(connection, post.getId()));
                posts.add(post);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public List<Post> getAllByAuthorId(Integer authorId) {
        List<Post> posts = new ArrayList<>();
        String SQL = "SELECT * FROM posts p " +
                "INNER JOIN writers w ON p.author_id = w.id " +
                "WHERE w.id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = getPostFromResultSet(resultSet);
                post.setLabels(getLabelsByPostId(connection, post.getId()));
                posts.add(post);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public Post update(Post post) {
        String SQL = "UPDATE posts SET content = ?, updated = ? WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, post.getContent());
            statement.setTimestamp(2, Timestamp.valueOf(post.getUpdated()));
            statement.setInt(3, post.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0){
                post = getById(post.getId());
                return post;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post updateStatus(Post post) {
        String SQL = "UPDATE posts SET status_id = ?, updated = ? WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, post.getPostStatus().getValue());
            statement.setTimestamp(2, Timestamp.valueOf(post.getUpdated()));
            statement.setInt(3, post.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0){
                post = getById(post.getId());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public boolean delete(Integer id) {
        String SQL = "UPDATE posts SET status_id = 3 WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        String SQL = "SELECT * FROM posts WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Post getPostFromResultSet(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getInt("id"));
        post.setContent(resultSet.getString("content"));
        LocalDateTime created = resultSet.getTimestamp("created").toLocalDateTime();
        post.setCreated(created);
        Timestamp timestamp = resultSet.getTimestamp("updated");
        LocalDateTime updated = timestamp != null ? timestamp.toLocalDateTime() : null;
        post.setUpdated(updated);
        post.setAuthorId(resultSet.getInt("author_id"));
        post.setPostStatus(PostStatus.fromValue(resultSet.getInt("status_id")));
        return post;
    }

    private List<Label> getLabelsByPostId(Connection connection, int id) {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM labels l " +
                "INNER JOIN post_label p ON l.id = p.label_id " +
                "WHERE p.post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setName(resultSet.getString("name"));
                labels.add(label);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return labels;
    }

}
