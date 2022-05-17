package com.valts.obforumdemov2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valts.obforumdemov2.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements CustomUserDetails {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "users_preguntas",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "pregunta_id", referencedColumnName = "id"
            )
    )
    private Set<Pregunta> followedPreguntas = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinTable(
//            name = "users_tema",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "tema_id", referencedColumnName = "id"
//            )
//    )
//    @ManyToMany(mappedBy = "followers")
//    private Set<Tema> followedTemas = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

//    public User(Long id, String avatar, String username) {
//        this.id = id;
//        this.avatar = avatar;
//        this.username = username;
//    }
}
