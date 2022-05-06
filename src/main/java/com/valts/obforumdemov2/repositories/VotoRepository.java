package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.Voto;
import com.valts.obforumdemov2.models.VotoPregunta;
import com.valts.obforumdemov2.models.VotoRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<VotoRespuesta> findByRespuestaIdAndUserId(Long voteTypeId, Long userId);

    Optional<VotoPregunta> findByPreguntaIdAndUserId(Long voteTypeId, Long userId);
}
