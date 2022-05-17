package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.FollowerTema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerTemaRepository extends JpaRepository<FollowerTema, Long> {
    Optional<FollowerTema> findByUserIdAndTemaId(Long userId, Long temaId);
}
