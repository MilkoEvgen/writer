package org.example.repository;

import org.example.model.Label;
import org.example.util.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    @Override
    public Label create(Label label) {
        String SQL = "INSERT INTO labels (name) VALUES (?)";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, label.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    label.setId(generatedKeys.getInt(1));
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }


    @Override
    public Label getById(Integer id) {
        Label label = null;
        String SQL = "SELECT * FROM labels WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM labels";
        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
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

    @Override
    public Label update(Label label) {
        String SQL = "UPDATE labels SET name = ? WHERE id = ?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return label;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String SQL = "DELETE FROM labels WHERE id = ?";
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
        String SQL = "SELECT * FROM labels WHERE id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Label> getLabelsByPostId(Integer postId) {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM labels l " +
                "INNER JOIN post_label p ON l.id = p.label_id " +
                "WHERE p.post_id = ?";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
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

    @Override
    public void addPostLabel(Integer postId, List<Integer> labelsId) {
        String SQL = "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            for (Integer labelId : labelsId) {
                statement.setInt(1, postId);
                statement.setInt(2, labelId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
