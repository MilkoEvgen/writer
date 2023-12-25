package org.example.service;

import org.example.exceptions.EntityNotFoundException;
import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PostServiceImpl implements PostService{
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
        for (Integer id : labelId) {
            if (!labelRepository.existsById(id)){
                throw new EntityNotFoundException("Label not exists");
            }
        }
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(writerRepository.getById(authorId));
        post.setCreated(LocalDateTime.now());
        post = postRepository.create(post);
        labelRepository.addPostLabel(post.getId(), labelId);
        post.setLabels(labelRepository.getLabelsByPostId(post.getId()));
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
        for (Post post : posts) {
            post.setLabels(labelRepository.getLabelsByPostId(post.getId()));
        }
        return posts;
    }

    @Override
    public List<Post> getAllByAuthorId(Integer authorId) {
        if (!writerRepository.existsById(authorId)){
            throw new EntityNotFoundException("Author not exists");
        }
        List<Post> posts = postRepository.getAllByAuthorId(authorId);
        for (Post post : posts) {
            post.setLabels(labelRepository.getLabelsByPostId(post.getId()));
        }
        return posts;
    }

    @Override
    public Post update(Integer id, String content) {
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException("Post not exists");
        }
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setUpdated(LocalDateTime.now());
        return postRepository.update(post);
    }

    @Override
    public Post updateStatus(Integer postId, Integer statusId) {
        if (!postRepository.existsById(postId)){
            throw new EntityNotFoundException("Post not exists");
        }
        Post post = new Post();
        post.setId(postId);
        post.setPostStatus(PostStatus.fromValue(statusId));
        post.setUpdated(LocalDateTime.now());
        return postRepository.updateStatus(post);
    }

    @Override
    public boolean delete(Integer id) {
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException("Post not exists");
        }
        return postRepository.delete(id);
    }

}
