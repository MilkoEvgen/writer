package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "labels", fetch = FetchType.LAZY)
    private List<Post> posts;

    @Override
    public String toString() {
        return "(" +
                "id=" + id +
                ", name='" + name + '\'' +
                ')';
    }
}
