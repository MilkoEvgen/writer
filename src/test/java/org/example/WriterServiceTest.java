package org.example;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.WriterRepository;
import org.example.service.WriterService;
import org.example.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class WriterServiceTest {
    @Mock
    private WriterRepository writerRepository;

    private WriterService writerService;

    private Writer writer;

    private Label label;

    private Post post;

    @BeforeEach
    void init(){
        writerService = new WriterServiceImpl(writerRepository);
        label = new Label();
        post = new Post();
        writer = new Writer();
        writer.setId(1);
        writer.setFirstName("first");
        writer.setLastName("last");
        writer.setPosts(List.of(post));
    }

    @Test
    public void createShouldReturnWriter(){
        Mockito.when(writerRepository.create(any())).thenReturn(writer);
        Writer actual = writerService.create("first", "last");
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("first", actual.getFirstName());
        Assertions.assertEquals("last", actual.getLastName());
        Assertions.assertEquals(1, actual.getPosts().size());
        Mockito.verify(writerRepository).create(any());
    }

    @Test
    public void createShouldReturnNull(){
        Mockito.when(writerRepository.create(any())).thenReturn(null);
        Writer actual = writerService.create("first", "last");
        Assertions.assertNull(actual);
        Mockito.verify(writerRepository).create(any());
    }

    @Test
    public void getByIdShouldReturnWriter(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(writerRepository.getById(1)).thenReturn(writer);
        Writer actual = writerService.getById(1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("first", actual.getFirstName());
        Assertions.assertEquals("last", actual.getLastName());
        Assertions.assertEquals(1, actual.getPosts().size());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(writerRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundException(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> writerService.getById(1));
        Assertions.assertEquals("Writer not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
    }

    @Test
    public void getAllShouldReturnListOfWriters(){
        Mockito.when(writerRepository.getAll()).thenReturn(List.of(writer));
        List<Writer> actual = writerService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("first", actual.get(0).getFirstName());
        Assertions.assertEquals("last", actual.get(0).getLastName());
        Assertions.assertEquals(1, actual.get(0).getPosts().size());
        Mockito.verify(writerRepository).getAll();
    }
    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(writerRepository.getAll()).thenReturn(List.of());
        List<Writer> actual = writerService.getAll();
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(writerRepository).getAll();
    }

    @Test
    public void updateShouldReturnWriter(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(writerRepository.update(any())).thenReturn(writer);
        Writer actual = writerService.update(1, "first", "last");
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("first", actual.getFirstName());
        Assertions.assertEquals("last", actual.getLastName());
        Assertions.assertEquals(1, actual.getPosts().size());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(writerRepository).update(any());
    }

    @Test
    public void updateShouldThrowEntityNotFoundException(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> writerService.update(1, "first", "last"));
        Assertions.assertEquals("Writer not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
    }

    @Test
    public void deleteShouldReturnTrue(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(writerRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(writerService.delete(1));
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(writerRepository).delete(1);
    }

    @Test
    public void deleteShouldThrowEntityNotFoundException(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> writerService.delete(1));
        Assertions.assertEquals("Writer not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
    }
}
