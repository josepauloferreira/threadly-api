package xyz.thaumazein.forumhub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user")
    private Set<Answer> answers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Topic> topics = new HashSet<>();


}