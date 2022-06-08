package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.Voto;
import com.valts.obforumdemov2.models.VotoPregunta;
import com.valts.obforumdemov2.models.VotoRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<VotoRespuesta> findByRespuestaIdAndUserId(Long voteTypeId, Long userId);

    Optional<VotoPregunta> findByPreguntaIdAndUserId(Long voteTypeId, Long userId);

    Optional<VotoPregunta> findByPreguntaIdAndUserIdAndVote(Long voteTypeId, Long userId, boolean b);

    Optional<VotoRespuesta> findByRespuestaIdAndUserIdAndVote(Long voteTypeId, Long userId, boolean b);

    List<VotoRespuesta> findAllByRespuesta_Id(Long respuestaId);
//    <T> List<T> findAllByRespuesta_Id(Long respuestaId);

    List<VotoPregunta> findAllByPregunta_Id(Long respuestaId);
//    <T> List<T> findAllByPregunta_Id(Long respuestaId);
}
