package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.models.Pregunta;

import java.util.List;

public interface PreguntaService {
    List<PreguntaDTO> findByTemaId(Long id);

    PreguntaUserVoteDTO findById(Long id, Long userId);

    Pregunta save(Pregunta pregunta, Long userId);

    Pregunta update(Pregunta pregunta);

    boolean deleteOne(Long id);
}
