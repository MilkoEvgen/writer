package org.example.repository;

import org.example.model.Label;

import java.sql.Connection;
import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Integer> {
    List<Label> getLabelsByPostId(Integer postId);

    void addPostLabel(Integer postId, List<Integer> labelsId);
}
