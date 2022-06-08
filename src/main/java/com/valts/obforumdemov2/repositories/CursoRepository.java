package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.Curso;
import com.valts.obforumdemov2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Query(value = "select c.* from cursos c left join cursos_users cu on c.id = cu.curso_id where cu.user_id = :userId ", nativeQuery = true)
    List<Curso> findAllByUser_Id(Long userId);

    @Query(value = "select c.* from cursos c left join cursos_users cu on c.id = cu.curso_id where cu.user_id = :userId and c.id = :cursoId ", nativeQuery = true)
    Curso findAllByUser_IdAndCursoId(Long userId, Long cursoId);
}
