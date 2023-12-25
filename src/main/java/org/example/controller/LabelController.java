package org.example.controller;

import org.example.model.Label;
import org.example.service.LabelService;

import java.util.List;

public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    public Label create(String name){
        return labelService.create(name);
    }

    public Label getById(Integer id){
        return labelService.getById(id);
    }

    public List<Label> getAll(){
        return labelService.getAll();
    }

    public Label update(Integer id, String name){
        return labelService.update(id, name);
    }

    public boolean delete(Integer id){
        return labelService.delete(id);
    }
}
