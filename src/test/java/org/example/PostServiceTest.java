package org.example;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;
import org.example.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private WriterRepository writerRepository;
    @Mock
    private LabelRepository labelRepository;
    @InjectMocks
    private PostServiceImpl postService;

    private Label label;

    private Writer author;

    private Post post;

    @BeforeEach
    void init(){
        label = new Label();
        label.setId(1);
        label.setName("label");
        post = new Post();
        post.setId(1);
        post.setContent("content");
        post.setCreated(LocalDateTime.of(2000, 12, 11, 10, 30));
        post.setLabels(List.of(label));
        author = new Writer();
        author.setId(1);
        post.setAuthor(author);
    }

    @Test
    public void createShouldReturnPost(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(labelRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.create(any())).thenReturn(post);
        Mockito.when(labelRepository.getLabelsByPostId(1)).thenReturn(List.of(label));
        Post actual = postService.create("content", 1, List.of(1));
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("content", actual.getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.getCreated());
        Assertions.assertEquals(1, actual.getLabels().size());
        Assertions.assertEquals(1, actual.getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.getAuthor().getId());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(labelRepository).existsById(1);
        Mockito.verify(postRepository).create(any());
        Mockito.verify(labelRepository).addPostLabel(1, List.of(1));
    }

    @Test
    public void createShouldThrowEntityNotFoundExceptionIfAuthorNotExists(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.create("content", 1, List.of()));
        Assertions.assertEquals("Author not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
    }

    @Test
    public void createShouldThrowEntityNotFoundExceptionIfLabelNotExists(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(labelRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.create("content", 1, List.of(1)));
        Assertions.assertEquals("Label not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(labelRepository).existsById(1);
    }

    @Test
    public void getByIdShouldReturnPost(){
        Mockito.when(postRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.getById(1)).thenReturn(post);
        Post actual = postService.getById(1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("content", actual.getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.getCreated());
        Assertions.assertEquals(1, actual.getLabels().size());
        Assertions.assertEquals(1, actual.getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.getAuthor().getId());
        Mockito.verify(postRepository).existsById(1);
        Mockito.verify(postRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionIfPostNotExists(){
        Mockito.when(postRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.getById(1));
        Assertions.assertEquals("Post not exists", e.getMessage());
        Mockito.verify(postRepository).existsById(1);
    }

    @Test
    public void getAllShouldReturnListOfPosts(){
        Mockito.when(postRepository.getAll()).thenReturn(List.of(post));
        Mockito.when(labelRepository.getLabelsByPostId(1)).thenReturn(List.of(label));
        List<Post> actual = postService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("content", actual.get(0).getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.get(0).getCreated());
        Assertions.assertEquals(1, actual.get(0).getLabels().size());
        Assertions.assertEquals(1, actual.get(0).getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.get(0).getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.get(0).getAuthor().getId());
        Mockito.verify(postRepository).getAll();
        Mockito.verify(labelRepository).getLabelsByPostId(1);
    }

    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(postRepository.getAll()).thenReturn(List.of());
        Assertions.assertEquals(0, postService.getAll().size());
        Mockito.verify(postRepository).getAll();
    }

    @Test
    public void getAllByAuthorIdShouldReturnListOfPosts(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.getAllByAuthorId(1)).thenReturn(List.of(post));
        Mockito.when(labelRepository.getLabelsByPostId(1)).thenReturn(List.of(label));
        List<Post> actual = postService.getAllByAuthorId(1);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("content", actual.get(0).getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.get(0).getCreated());
        Assertions.assertEquals(1, actual.get(0).getLabels().size());
        Assertions.assertEquals(1, actual.get(0).getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.get(0).getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.get(0).getAuthor().getId());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(postRepository).getAllByAuthorId(1);
        Mockito.verify(labelRepository).getLabelsByPostId(1);
    }

    @Test
    public void getAllByAuthorIdShouldReturnReturnEmptyList(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.getAllByAuthorId(1)).thenReturn(List.of());
        List<Post> actual = postService.getAllByAuthorId(1);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(writerRepository).existsById(1);
        Mockito.verify(postRepository).getAllByAuthorId(1);
    }

    @Test
    public void getAllByAuthorIdShouldThrowEntityNotFoundExceptionIfAuthorNotExists(){
        Mockito.when(writerRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.getAllByAuthorId(1));
        Assertions.assertEquals("Author not exists", e.getMessage());
        Mockito.verify(writerRepository).existsById(1);
    }

    @Test
    public void updateShouldReturnPost(){
        Mockito.when(postRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.update(any())).thenReturn(post);
        post.setUpdated(LocalDateTime.of(2020, 12, 11, 10, 30));
        Post actual = postService.update(1, "content");
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("content", actual.getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.getCreated());
        Assertions.assertEquals(LocalDateTime.of(2020, 12, 11, 10, 30), actual.getUpdated());
        Assertions.assertEquals(1, actual.getLabels().size());
        Assertions.assertEquals(1, actual.getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.getAuthor().getId());
        Mockito.verify(postRepository).existsById(1);
        Mockito.verify(postRepository).update(any());
    }

    @Test
    public void updateShouldThrowEntityNotFoundExceptionIfPostNotExists(){
        Mockito.when(postRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.update(1, "content"));
        Assertions.assertEquals("Post not exists", e.getMessage());
        Mockito.verify(postRepository).existsById(1);
    }

    @Test
    public void updateStatusShouldReturnPost(){
        post.setUpdated(LocalDateTime.of(2020, 12, 11, 10, 30));
        Mockito.when(postRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.updateStatus(any())).thenReturn(post);
        Post actual = postService.updateStatus(1, 1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("content", actual.getContent());
        Assertions.assertEquals(LocalDateTime.of(2000, 12, 11, 10, 30), actual.getCreated());
        Assertions.assertEquals(LocalDateTime.of(2020, 12, 11, 10, 30), actual.getUpdated());
        Assertions.assertEquals(1, actual.getLabels().size());
        Assertions.assertEquals(1, actual.getLabels().get(0).getId());
        Assertions.assertEquals("label", actual.getLabels().get(0).getName());
        Assertions.assertEquals(1, actual.getAuthor().getId());
        Mockito.verify(postRepository).existsById(1);
        Mockito.verify(postRepository).updateStatus(any());
    }

    @Test
    public void updateStatusShouldThrowEntityNotFoundExceptionIfPostNotExists(){
        Mockito.when(postRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.updateStatus(1, 1));
        Assertions.assertEquals("Post not exists", e.getMessage());
        Mockito.verify(postRepository).existsById(1);
    }

    @Test
    public void deleteShouldReturnTrue(){
        Mockito.when(postRepository.existsById(1)).thenReturn(true);
        Mockito.when(postRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(postService.delete(1));
        Mockito.verify(postRepository).existsById(1);
        Mockito.verify(postRepository).delete(1);
    }

    @Test
    public void deleteShouldThrowEntityNotFoundException(){
        Mockito.when(postRepository.existsById(1)).thenReturn(false);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> postService.delete(1));
        Assertions.assertEquals("Post not exists", e.getMessage());
        Mockito.verify(postRepository).existsById(1);
    }
}
