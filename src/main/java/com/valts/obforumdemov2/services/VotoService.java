package com.valts.obforumdemov2.services;

import com.valts.obforumdemov2.models.Voto;

public interface VotoService {
    boolean save(Long userId, Voto vote, Long voteTypeId, String voteType);

    boolean update(Long userId, Long voteTypeId, String voteType);

    boolean delete(Long userId, Long voteTypeId, String voteType);
}
