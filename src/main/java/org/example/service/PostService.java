package org.example.service;

import org.example.model.Label;
import org.example.model.Post;

import java.util.List;

public interface PostService {
    Post create(String content, Integer authorId, List<Integer> labels);
    Post getById(Integer id);
    List<Post> getAll();
    List<Post> getAllByAuthorId(Integer authorId);
    Post update(Integer id, String content);
    Post updateStatus(Integer postId, Integer statusId);
    boolean delete(Integer id);
}
