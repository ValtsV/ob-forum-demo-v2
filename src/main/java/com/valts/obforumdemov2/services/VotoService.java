package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.models.Voto;
import com.valts.obforumdemov2.models.VotoPregunta;
import com.valts.obforumdemov2.models.VotoRespuesta;

import java.util.List;

public interface VotoService {
    List<VotoRespuesta> saveRespuesta(Long userId, Voto vote, Long voteTypeId);

    List<VotoPregunta> savePregunta(Long userId, Voto vote, Long voteTypeId);

    boolean update(Long userId, Long voteTypeId, String voteType);

    boolean delete(Long userId, Long voteTypeId, String voteType);


}
