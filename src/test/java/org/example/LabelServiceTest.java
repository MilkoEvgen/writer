package org.example;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.service.LabelService;
import org.example.service.LabelServiceImpl;
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
public class LabelServiceTest {

    @Mock
    private LabelRepository labelRepository;
    private LabelService labelService;

    private Label label;

    @BeforeEach
    void init(){
        labelService = new LabelServiceImpl(labelRepository);
        label = new Label();
        label.setId(1);
        label.setName("label");
    }

    @Test
    public void createShouldReturnLabel(){
        Mockito.when(labelRepository.create(any())).thenReturn(label);
        Label actual = labelService.create(any());
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("label", actual.getName());
        Mockito.verify(labelRepository).create(any());
    }

    @Test
    public void createShouldReturnNull(){
        Mockito.when(labelRepository.create(any())).thenReturn(null);
        Label actual = labelService.create(any());
        Assertions.assertNull(actual);
        Mockito.verify(labelRepository).create(any());
    }

    @Test
    public void getByIdShouldReturnLabel(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(true);
        Mockito.when(labelRepository.getById(1)).thenReturn(label);
        Label actual = labelService.getById(1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("label", actual.getName());
        Mockito.verify(labelRepository).existsById(1);
        Mockito.verify(labelRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundException(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> labelService.getById(1));
        Assertions.assertEquals("Label not exists", e.getMessage());
        Mockito.verify(labelRepository).existsById(1);
    }

    @Test
    public void getAllShouldReturnListOfLabels(){
        Mockito.when(labelRepository.getAll()).thenReturn(List.of(label));
        List<Label> actual = labelService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("label", actual.get(0).getName());
        Mockito.verify(labelRepository).getAll();
    }

    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(labelRepository.getAll()).thenReturn(List.of());
        List<Label> actual = labelService.getAll();
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(labelRepository).getAll();
    }

    @Test
    public void updateShouldReturnLabel(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(true);
        Mockito.when(labelRepository.update(any())).thenReturn(label);
        Label actual = labelService.update(1, "label");
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("label", actual.getName());
        Mockito.verify(labelRepository).existsById(1);
        Mockito.verify(labelRepository).update(any());
    }

    @Test
    public void updateShouldThrowEntityNotFoundException(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> labelService.update(1, "label"));
        Assertions.assertEquals("Label not exists", e.getMessage());
        Mockito.verify(labelRepository).existsById(any());
    }

    @Test
    public void deleteShouldReturnTrue(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(true);
        Mockito.when(labelRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(labelService.delete(1));
        Mockito.verify(labelRepository).existsById(1);
        Mockito.verify(labelRepository).delete(1);
    }

    @Test
    public void deleteShouldThrowEntityNotFoundException(){
        Mockito.when(labelRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> labelService.delete(1));
        Assertions.assertEquals("Label not exists", e.getMessage());
        Mockito.verify(labelRepository).existsById(1);
    }
}
