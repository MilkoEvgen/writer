package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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
