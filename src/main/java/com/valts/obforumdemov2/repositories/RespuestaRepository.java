package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.dto.RespuestaDTO;
import com.valts.obforumdemov2.models.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Query(value = "select new com.valts.obforumdemov2.dto.RespuestaDTO(r.id, r.updatedAt, r.answer, r.isPinned, r.pregunta.id, count(case when v.vote = true then 1 end) as totalPositiveVotes, count(case when v.vote = false then 1 end) as totalNegativeVotes, u.id as userId, u.avatar as avatar, u.username as username, (select vr.vote from r.votos vr where vr.user.id = :userId and vr.respuesta.id = r.id) as userVote) from Respuesta r left join r.votos v left join r.user u where r.pregunta.id = :preguntaId group by r.id, u.id order by r.isPinned desc, totalPositiveVotes desc")
    List<RespuestaDTO> findRespuestasByPreguntaId(Long preguntaId, Long userId);
}
