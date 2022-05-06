package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.dto.RespuestaDTO;
import com.valts.obforumdemov2.models.Respuesta;

import java.util.List;

public interface RespuestaService {
    List<RespuestaDTO> findByPreguntaId(Long preguntaId, Long userId);

    List<RespuestaDTO> save(Respuesta respuesta, Long userId);

    List<RespuestaDTO> update(Respuesta respuesta);

    List<RespuestaDTO> deleteOne(Long preguntaId, Long userId);

}
