package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.models.Pregunta;
import com.valts.obforumdemov2.services.implementations.PreguntaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class PreguntasController {

    private final PreguntaServiceImpl preguntaService;

    @GetMapping("/foro/preguntas/temas/{temaId}")
    private ResponseEntity<List<PreguntaDTO>> getPreguntasByTemaId(@PathVariable Long temaId) {
        List<PreguntaDTO> preguntas = preguntaService.findByTemaId(temaId);
        if (preguntas.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(preguntas);
    }

    @GetMapping("/foro/preguntas/{preguntaId}")
    private ResponseEntity<PreguntaUserVoteDTO> getPreguntaById(@PathVariable Long preguntaId) {
        //    TODO: Get User Id from security context
        Long userId = 3L;
        PreguntaUserVoteDTO pregunta = preguntaService.findById(preguntaId, userId);
        if (pregunta == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pregunta);
    }

//    TODO: Get User Id from security context
    @PostMapping("/foro/preguntas")
    private ResponseEntity<Pregunta> addPregunta(@RequestBody Pregunta pregunta) {
        if(pregunta.getTemaId() == null) return ResponseEntity.badRequest().build();

        Long dummyUserId = 1L;
        Pregunta savedPregunta = preguntaService.save(pregunta, dummyUserId);
        if (savedPregunta == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(savedPregunta);
    }

//    user who created + admin
    @PutMapping("/foro/preguntas")
    private ResponseEntity<Pregunta> updatePregunta(@RequestBody Pregunta pregunta) {
        if (pregunta.getId() == null) return ResponseEntity.badRequest().build();

//        TODO: Run check against user id match here
        Pregunta updatedPregunta = preguntaService.update(pregunta);
        if (updatedPregunta != null) return ResponseEntity.ok(updatedPregunta);

        return ResponseEntity.notFound().build();
    }

//    user who created + admin
    @DeleteMapping("/foro/preguntas/{id}")
    private ResponseEntity<Void> deletePregunta(@PathVariable Long id) {
        boolean isDeleted = preguntaService.deleteOne(id);

        if (isDeleted) return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
