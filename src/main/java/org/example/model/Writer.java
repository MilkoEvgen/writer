package org.example.model;

import java.util.List;
import java.util.stream.Collectors;

public class Writer {
    private Integer id;
    private String  firstName;
    private String  lastName;
    private List<Post> posts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        String postsAsString = posts != null
                ? posts.stream()
                .map(post -> "{id=" + post.getId() +
                        ", content=" + post.getContent() +
                        ", created=" + post.getCreated() +
                        ", updated=" + post.getUpdated() +
                        ", labels=" + post.getLabels() +
                        ", status=" + post.getPostStatus() + "}")
                .collect(Collectors.joining("\n"))
                : "[]";

        return "(" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=\n" + postsAsString +
                ')';
    }


}
