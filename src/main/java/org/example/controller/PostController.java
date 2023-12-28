package org.example.controller;

import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public Post create(String content, Integer authorId, List<Integer> labels){
        return postService.create(content, authorId, labels);
    }

    public Post getById(Integer id){
        return postService.getById(id);
    }

    public List<Post> getAll(){
        return postService.getAll();
    }

    public List<Post> getAllByAuthorId(Integer authorId){
        return postService.getAllByAuthorId(authorId);
    }

    public Post update(Integer id, String content, PostStatus status){
        return postService.update(id, content, status);
    }

    public boolean delete(Integer id){
        return postService.delete(id);
    }
}
