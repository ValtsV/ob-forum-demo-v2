package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.models.VotoPregunta;
import com.valts.obforumdemov2.models.VotoRespuesta;
import com.valts.obforumdemov2.services.implementations.VotosServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class VotosController {

    private final VotosServiceImpl votosService;

    @PostMapping("/foro/votos/respuestas/{respuestaId}")
    private ResponseEntity<Void> addVotoRespuesta(@RequestBody VotoRespuesta vote, @PathVariable Long respuestaId) {
        Long userId = 3L;
        boolean isVoted = votosService.save(userId, vote, respuestaId, "respuesta");
        if (!isVoted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/foro/votos/preguntas/{preguntaId}")
    private ResponseEntity<Void> addVotoPregunta(@RequestBody VotoPregunta vote, @PathVariable Long preguntaId) {
        Long userId = 3L;
        boolean isVoted = votosService.save(userId, vote, preguntaId, "pregunta");
        if (!isVoted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    //    switches from like to dislike and vice versa
    @PutMapping("/foro/votos/respuestas/{respuestaId}")
    private ResponseEntity<Void> updateVotoRespuesta(@PathVariable Long respuestaId) {
        Long userId = 3L;
        boolean isVoted = votosService.update(userId, respuestaId, "respuesta");
        if (!isVoted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

//    switches from like to dislike and vice versa
    @PutMapping("/foro/votos/preguntas/{preguntaId}")
    private ResponseEntity<Void> updateVotoPregunta(@PathVariable Long preguntaId) {
        Long userId = 3L;
        boolean isVoted = votosService.update(userId, preguntaId, "pregunta");
        if (!isVoted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/foro/votos/respuestas/{respuestaId}")
    private ResponseEntity<Void> deleteVoto(@PathVariable Long respuestaId) {
        Long userId = 3L;
        boolean isDeleted = votosService.delete(userId, respuestaId, "respuesta");
        if (!isDeleted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }
}
