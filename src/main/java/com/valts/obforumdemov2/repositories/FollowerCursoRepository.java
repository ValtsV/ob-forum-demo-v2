package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.FollowerCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerCursoRepository extends JpaRepository<FollowerCurso, Long> {
    Optional<FollowerCurso> findByUserIdAndCursoId(Long userId, Long cursoId);
}
