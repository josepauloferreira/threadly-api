package xyz.thaumazein.forumhub.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String message;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "solution")
    private Boolean solution;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}