package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.TemaDTO;
import com.valts.obforumdemov2.models.Tema;
import com.valts.obforumdemov2.services.implementations.TemaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class TemasController {

    private final TemaServiceImpl temaService;

    @GetMapping("/foro/temas/cursos/{cursoId}")
    private ResponseEntity<List<TemaDTO>> getTemasByCursoId(@PathVariable Long cursoId) {
        List<TemaDTO> temas = temaService.findByCursoId(cursoId);

        if (temas.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(temas);
    }

    @GetMapping("/foro/temas/cursos/{cursoId}/modulos/{moduloId}")
    private ResponseEntity<List<TemaDTO>> getTemasByModuloId(@PathVariable Long cursoId, @PathVariable Long moduloId) {
        List<TemaDTO> temas = temaService.findByCursoIdAndModuloId(cursoId, moduloId);

        if (temas.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(temas);
    }

//    check if its needed to throw exception or it happens auto
    @GetMapping("/foro/temas/{id}")
    private ResponseEntity<Tema> getTemaById(@PathVariable Long id) {
        Tema tema = temaService.findById(id);

        return ResponseEntity.ok(tema);
    }

//    Admin only
    @PostMapping("/foro/temas")
    private ResponseEntity<Tema> addTema(@RequestBody Tema tema) {
        if (tema.getCursoId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Tema savedTema = temaService.save(tema);
        if (savedTema == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTema);
    }

//    Admin only
    @PutMapping("/foro/temas")
    private ResponseEntity<Tema> updateTema(@RequestBody Tema tema) {
        if (tema.getId() == null) return ResponseEntity.badRequest().build();

        if (tema.getCursoId() == null) return ResponseEntity.badRequest().build();

        Tema updatedTema = temaService.update(tema);
        if (updatedTema != null) return ResponseEntity.ok(updatedTema);

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/foro/temas/{id}")
    private ResponseEntity<Void> deleteTema(@PathVariable Long id) {
        boolean isDeleted =  temaService.deleteOne(id);
        if (isDeleted) return ResponseEntity.noContent().build();

        return ResponseEntity.badRequest().build();
    }

}
