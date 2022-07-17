package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.exceptions.IncorrectUserException;
import com.valts.obforumdemov2.models.Pregunta;
import com.valts.obforumdemov2.models.User;

import java.util.List;

public interface PreguntaService {
    List<PreguntaDTO> findByTemaId(Long id);

    PreguntaUserVoteDTO findById(Long id, Long userId);

    Pregunta save(Pregunta pregunta, Long userId);

    Pregunta update(Pregunta pregunta, User user, boolean isAdmin) throws IncorrectUserException;

    boolean deleteOne(Long id, User user);
}
