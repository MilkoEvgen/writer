package org.example.repository;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.model.Writer;
import org.example.util.DbUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer create(Writer writer) {
        String SQL = "INSERT INTO writers (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    writer = getById(id);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    @Override
    public Writer getById(Integer id) {
        Writer writer = null;
        List<Post> posts = new ArrayList<>();
        String SQL = "SELECT writers.*, posts.* " +
                "FROM writers " +
                "LEFT JOIN posts ON writers.id = posts.author_id " +
                "WHERE writers.id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (writer == null) {
                    writer = getWriterFromResultSet(resultSet);
                }
                Post post = getPostFromResultSet(resultSet);
                if (post != null){
                    post.setLabels(getLabelsByPostId(connection, post.getId()));
                    posts.add(post);
                    writer.setPosts(posts);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        String SQL = "SELECT * FROM writers";
        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Writer writer = getWriterFromResultSet(resultSet);
                writers.add(writer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    @Override
    public Writer update(Writer writer) {
        String SQL = "UPDATE writers SET first_name = ?, last_name - ? WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setInt(3, writer.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    writer = getById(id);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    @Override
    public boolean delete(Integer id) {
        String SQL = "DELETE FROM writers WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
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
        String SQL = "SELECT * FROM writers WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Writer getWriterFromResultSet(ResultSet resultSet) throws SQLException {
        Writer writer = new Writer();
        writer.setId(resultSet.getInt("id"));
        writer.setFirstName(resultSet.getString("first_name"));
        writer.setLastName(resultSet.getString("last_name"));
        return writer;
    }

    private Post getPostFromResultSet(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        int id = resultSet.getInt(4);
        if (id > 0){
            post.setId(id);
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
        return null;
    }

    private List<Label> getLabelsByPostId(Connection connection, int postId) {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM labels l " +
                "INNER JOIN post_label p ON l.id = p.label_id " +
                "WHERE p.post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
