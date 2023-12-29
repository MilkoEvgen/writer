package org.example.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;
    @Column(name = "updated")
    private LocalDateTime updated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_label",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Writer author;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus postStatus;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", author={" +
                "id=" + author.getId() +
                ", first name=" + author.getFirstName() +
                ", last name=" + author.getLastName() +
                "}, postStatus=" + postStatus +
                "}";
    }
}
