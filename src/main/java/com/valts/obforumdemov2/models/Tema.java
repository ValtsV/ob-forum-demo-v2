package com.valts.obforumdemov2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valts.obforumdemov2.dto.TemaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temas")

@NamedNativeQueries({
        @NamedNativeQuery(name = "findAllByCursoIdUserAccessible",
                query = """
                        select t.id, t.description, t.is_pinned, t.title, t.curso_id, t.modulo_id, count(p) as preguntaCount from cursos_users c join temas t on (c.curso_id = t.curso_id) join preguntas p on (t.id = p.tema_id) where c.user_id = :userId and t.curso_id = :cursoId group by t.id order by t.is_pinned desc, t.id
                        """,
                resultSetMapping = "Mapping.TemaDTO"
        ),
        @NamedNativeQuery(name = "findAllByCursoIdAndModuloIdUserAccessible",
                query = """
                        select t.id, t.description, t.is_pinned, t.title, t.curso_id, t.modulo_id, count(p) as preguntaCount from cursos_users c join temas t on (c.curso_id = t.curso_id) join preguntas p on (t.id = p.tema_id) where c.user_id = :userId and t.curso_id = :cursoId and t.modulo_id = :moduloId group by t.id order by t.is_pinned desc, t.id
                        """,
                resultSetMapping = "Mapping.TemaDTO"
        )}
)
@SqlResultSetMapping(name = "Mapping.TemaDTO",
                    classes = {
                        @ConstructorResult(targetClass = TemaDTO.class,
                            columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "description"),
                                @ColumnResult(name = "is_pinned"),
                                @ColumnResult(name = "title"),
                                @ColumnResult(name = "curso_id", type = Long.class),
                                @ColumnResult(name = "modulo_id", type = Long.class),
                                    @ColumnResult(name = "preguntaCount", type = Long.class)
                            })
                    }
)

public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean isPinned = false;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @JsonIgnore
    private Curso curso;

    @Column(name = "curso_id", insertable = false, updatable = false)
    private Long cursoId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    @JsonIgnore
    private Modulo modulo;

    @Column(name = "modulo_id", insertable = false, updatable = false)
    private Long moduloId;

//    @ManyToMany(mappedBy = "followedTemas")
//    @ManyToMany(fetch = FetchType.LAZY)
//        @JsonIgnore
//        @JoinTable(
//                name = "users_tema",
//                joinColumns = @JoinColumn(
//                        name = "tema_id", referencedColumnName = "id"
//                ),
//                inverseJoinColumns = @JoinColumn(
//                        name = "user_id", referencedColumnName = "id"
//                )
//        )
//    private Set<User> followers = new HashSet<>();

//    public Tema(Long id, String description, boolean isPinned, String title, Long cursoId, Long moduloId) {
//        this.id = id;
//        this.description = description;
//        this.isPinned = isPinned;
//        this.title = title;
//        this.cursoId = cursoId;
//        this.moduloId = moduloId;
//    }
}
