package com.valts.obforumdemov2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String email;
    private boolean enabled = true;
    private boolean locked = false;
    private String password;
    private String username;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    },
    fetch = FetchType.LAZY)
    @JoinTable(name = "cursos_users",
        joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();

//    public User(Long id, String avatar, String username) {
//        this.id = id;
//        this.avatar = avatar;
//        this.username = username;
//    }
}
