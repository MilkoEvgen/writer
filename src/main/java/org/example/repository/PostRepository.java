package org.example.repository;

import org.example.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Integer> {
    List<Post> getAllByAuthorId(Integer authorId);
    Post updateStatus(Post post);
}
