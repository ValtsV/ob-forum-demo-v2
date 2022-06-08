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

    @Query(value = "select new com.valts.obforumdemov2.dto.TemaDTO(t.id, t.description, t.isPinned, t.title, t.cursoId, t.moduloId, count(p) as preguntasCount) from Tema t left join Pregunta p on p.tema.id = t.id where t.id = :temaId group by t.id")
    TemaDTO findTemaWithPreguntaCountByTemaId(Long temaId);

//    @Query(value = "select t from Tema t left join fetch t.followers where t.id = :temaId")
//    Optional<Tema> findByIdWithFollowers(Long temaId);
}
