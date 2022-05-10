package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.exceptions.IncorrectUserException;
import com.valts.obforumdemov2.models.Pregunta;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.PreguntaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private ResponseEntity<PreguntaUserVoteDTO> getPreguntaById(@PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();

        PreguntaUserVoteDTO pregunta = preguntaService.findById(preguntaId, userDetails.getId());
        if (pregunta == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pregunta);
    }

    @PostMapping("/foro/preguntas")
    private ResponseEntity<Pregunta> addPregunta(@RequestBody Pregunta pregunta, Authentication authentication) {
        if(pregunta.getTemaId() == null) return ResponseEntity.badRequest().build();

        User userDetails = (User) authentication.getPrincipal();
        Pregunta savedPregunta = preguntaService.save(pregunta, userDetails.getId());
        if (savedPregunta == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(savedPregunta);
    }

    @PutMapping("/foro/preguntas")
    private ResponseEntity<Pregunta> updatePregunta(@RequestBody Pregunta pregunta, Authentication authentication) throws IncorrectUserException {
        if (pregunta.getId() == null) return ResponseEntity.badRequest().build();

        Pregunta updatedPregunta = preguntaService.update(pregunta, (User) authentication.getPrincipal());
        if (updatedPregunta != null) return ResponseEntity.ok(updatedPregunta);

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/foro/preguntas/{id}")
    private ResponseEntity<Void> deletePregunta(@PathVariable Long id, Authentication authentication) {
        boolean isDeleted = preguntaService.deleteOne(id, (User) authentication.getPrincipal());

        return ResponseEntity.noContent().build();
//        if (isDeleted) return ResponseEntity.noContent().build();

//        return ResponseEntity.notFound().build();
    }
}
