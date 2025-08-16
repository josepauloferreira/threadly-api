package xyz.thaumazein.forumhub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Course> courses = new HashSet<>();

}