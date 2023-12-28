package org.example.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "writers")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String  firstName;
    @Column(name = "last_name")
    private String  lastName;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
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
