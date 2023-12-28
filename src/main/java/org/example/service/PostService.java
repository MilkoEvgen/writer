package org.example.service;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.PostStatus;

import java.util.List;

public interface PostService {
    Post create(String content, Integer authorId, List<Integer> labels);
    Post getById(Integer id);
    List<Post> getAll();
    List<Post> getAllByAuthorId(Integer authorId);
    Post update(Integer id, String content, PostStatus status);
    boolean delete(Integer id);
}
