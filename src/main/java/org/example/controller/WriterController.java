package org.example.controller;

import org.example.model.Writer;
import org.example.service.WriterService;

import java.util.List;

public class WriterController {
    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    public Writer create(String firstName, String lastName){
        return writerService.create(firstName, lastName);
    }

    public Writer getById(Integer id){
        return writerService.getById(id);
    }

    public List<Writer> getAll(){
        return writerService.getAll();
    }

    public Writer update(Integer id, String firstName, String lastName){
        return writerService.update(id, firstName, lastName);
    }

    public boolean delete(Integer id){
        return writerService.delete(id);
    }
}
