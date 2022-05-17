package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.dto.TemaDTO;
import com.valts.obforumdemov2.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    @Query(name = "findAllByCursoIdUserAccessible", nativeQuery = true)
    List<TemaDTO> findAllByCursoIdUserAccessible(Long cursoId, Long userId);

    @Query(name = "findAllByCursoIdAndModuloIdUserAccessible", nativeQuery = true)
    List<TemaDTO> findAllByCursoIdAndModuloIdUserAccessible(Long cursoId, Long moduloId, Long userId);

//    @Query(value = "select t from Tema t left join fetch t.followers where t.id = :temaId")
//    Optional<Tema> findByIdWithFollowers(Long temaId);
}
