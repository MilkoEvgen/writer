package org.example.service.impl;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Label create(String name) {
        Label label = new Label();
        label.setName(name);
        return labelRepository.create(label);
    }

    @Override
    public Label getById(Integer id) {
        if (!labelRepository.existsById(id)){
            throw new EntityNotFoundException("Label not exists");
        }
        return labelRepository.getById(id);
    }

    @Override
    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    @Override
    public Label update(Integer id, String name) {
        if (!labelRepository.existsById(id)){
            throw new EntityNotFoundException("Label not exists");
        }
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        return labelRepository.update(label);
    }

    @Override
    public boolean delete(Integer id) {
        if (!labelRepository.existsById(id)){
            throw new EntityNotFoundException("Label not exists");
        }
        return labelRepository.delete(id);
    }
}
