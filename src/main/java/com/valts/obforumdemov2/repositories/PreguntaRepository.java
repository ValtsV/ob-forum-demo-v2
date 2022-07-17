package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.models.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    @Query(value = "select new com.valts.obforumdemov2.dto.PreguntaDTO(p.id, p.updatedAt, p.description, p.isPinned, p.title, count(case when v.vote = true then 1 end) as totalPositiveVotes, count(case when v.vote = false then 1 end) as totalNegativeVotes, (select count(r) as respuestas from p.respuestas r), u.id as userId, u.avatar as avatar, u.username as username) from Pregunta p left join p.votos v left join p.user u where p.temaId = :id group by p.id, u.id")
    List<PreguntaDTO> findAllByTemaId(Long id);

    @Query(value = "select new com.valts.obforumdemov2.dto.PreguntaUserVoteDTO(p.id, p.updatedAt, p.description, p.isPinned, p.title, count(case when v.vote = true then 1 end) as totalPositiveVotes, count(case when v.vote = false then 1 end) as totalNegativeVotes, (select count(r) as respuestas from p.respuestas r), u.id as userId, u.avatar as avatar, u.username as username, (SELECT vp.vote FROM VotoPregunta vp WHERE vp.pregunta.id = :preguntaId and vp.user.id = :userId) as userVote) from Pregunta p left join p.votos v left join p.user u where p.id = :preguntaId group by p.id, u.id")
    Optional<PreguntaUserVoteDTO> findPreguntaById(Long preguntaId, Long userId);

//    @Query(value = "select u.id from Pregunta p left join p.respuestas r on r.pregunta.id = p.id where r.id = :respuestaId")
//    Long findUserIdWherePreguntaId(Long preguntaId);

}

