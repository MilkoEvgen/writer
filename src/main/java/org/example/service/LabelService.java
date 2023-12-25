package org.example.service;

import org.example.model.Label;
import org.example.model.Writer;

import java.util.List;

public interface LabelService {
    Label create(String name);
    Label getById(Integer id);
    List<Label> getAll();
    Label update(Integer id, String name);
    boolean delete(Integer id);
}
