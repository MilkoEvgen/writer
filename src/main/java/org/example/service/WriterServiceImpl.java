package org.example.service;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Writer;
import org.example.repository.WriterRepository;

import java.util.List;

public class WriterServiceImpl implements WriterService{
    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public Writer create(String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.create(writer);
    }

    @Override
    public Writer getById(Integer id) {
        if (!writerRepository.existsById(id)){
            throw new EntityNotFoundException("Writer not exists");
        }
        return writerRepository.getById(id);
    }

    @Override
    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    @Override
    public Writer update(Integer id, String firstName, String lastName) {
        if (!writerRepository.existsById(id)){
            throw new EntityNotFoundException("Writer not exists");
        }
        Writer writer = new Writer();
        writer.setId(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.update(writer);
    }

    @Override
    public boolean delete(Integer id) {
        if (!writerRepository.existsById(id)){
            throw new EntityNotFoundException("Writer not exists");
        }
        return writerRepository.delete(id);
    }
}
