package org.example.service.impl;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;
import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;
import org.example.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final WriterRepository writerRepository;
    private final LabelRepository labelRepository;
    public PostServiceImpl(PostRepository postRepository, WriterRepository writerRepository, LabelRepository labelRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public Post create(String content, Integer authorId, List<Integer> labelId) {
        if (!writerRepository.existsById(authorId)){
            throw new EntityNotFoundException("Author not exists");
        }
        List<Label> labels = new ArrayList<>();
        for (Integer id : labelId) {
            if (!labelRepository.existsById(id)){
                throw new EntityNotFoundException("Label not exists");
            } else {
                labels.add(labelRepository.getById(id));
            }
        }
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(writerRepository.getById(authorId));
        post.setCreated(LocalDateTime.now());
        post.setLabels(labels);
        post.setPostStatus(PostStatus.UNDER_REVIEW);
        post = postRepository.create(post);
        return post;
    }

    @Override
    public Post getById(Integer id) {
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException("Post not exists");
        }
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = postRepository.getAll();
        return posts;
    }

    @Override
    public List<Post> getAllByAuthorId(Integer authorId) {
        if (!writerRepository.existsById(authorId)){
            throw new EntityNotFoundException("Author not exists");
        }
        return postRepository.getAllByAuthorId(authorId);
    }

    @Override
    public Post update(Integer id, String content, PostStatus status) {
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException("Post not exists");
        }
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setUpdated(LocalDateTime.now());
        post.setPostStatus(status);
        return postRepository.update(post);
    }

    @Override
    public boolean delete(Integer id) {
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException("Post not exists");
        }
        return postRepository.delete(id);
    }

}
