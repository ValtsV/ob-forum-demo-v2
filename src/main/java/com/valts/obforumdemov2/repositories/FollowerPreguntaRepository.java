package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.FollowerPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerPreguntaRepository extends JpaRepository<FollowerPregunta, Long> {
    Optional<FollowerPregunta> findByUserIdAndPreguntaId(Long userId, Long preguntaId);

}
