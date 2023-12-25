package org.example.service;

import org.example.model.Writer;

import java.util.List;

public interface WriterService {
    Writer create(String firstName, String lastName);
    Writer getById(Integer id);
    List<Writer> getAll();
    Writer update(Integer id, String firstName, String lastName);
    boolean delete(Integer id);
}
