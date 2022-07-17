package com.valts.obforumdemov2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(length = 4000)
    private String description;
    @Column(name = "is_pinned")
    private boolean isPinned = false;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id")
    @JsonIgnore
    private Tema tema;

    @Column(name = "tema_id", insertable = false, updatable = false)
    private Long temaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "pregunta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Respuesta> respuestas;

    @OneToMany(mappedBy = "pregunta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VotoPregunta> votos;

    @ManyToMany(mappedBy = "followedPreguntas")
    @JsonIgnore
    private Set<User> followers = new HashSet<>();
}
