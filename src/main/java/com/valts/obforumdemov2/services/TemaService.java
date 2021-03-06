package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.dto.TemaDTO;
import com.valts.obforumdemov2.models.Tema;
import com.valts.obforumdemov2.models.User;

import java.util.List;

public interface TemaService {
    List<TemaDTO> findByCursoId(Long id, Long userId);

    List<TemaDTO> findByCursoIdAndModuloId(Long cursoId, Long moduloId, Long userId);

    Tema findById(Long temaId);

    Tema save(Tema tema);

    Tema update(Tema tema);

    boolean deleteOne(Long id);
}
