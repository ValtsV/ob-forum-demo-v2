package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.RespuestaDTO;
import com.valts.obforumdemov2.models.Respuesta;
import com.valts.obforumdemov2.services.implementations.RespuestaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class RespuestasController {

    private final RespuestaServiceImpl respuestaService;

    @GetMapping("/foro/respuestas/preguntas/{preguntaId}")
    private ResponseEntity<List<RespuestaDTO>> getRespuestasByPreguntaId(@PathVariable Long preguntaId) {
        if (preguntaId == null) return ResponseEntity.badRequest().build();
        Long userId = 11L;
        List<RespuestaDTO> respuestas = respuestaService.findByPreguntaId(preguntaId, userId);
        if (respuestas.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(respuestas);
    }

    @PostMapping("/foro/respuestas")
    private ResponseEntity<List<RespuestaDTO>> addRespuesta(@RequestBody Respuesta respuesta){
        if (respuesta.getPreguntaId() == null) return ResponseEntity.badRequest().build();

//        TODO: get user id from security context
        Long userId = 3L;
        List<RespuestaDTO> respuestas = respuestaService.save(respuesta, userId);

        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }

    @PutMapping("/foro/respuestas")
    private ResponseEntity<List<RespuestaDTO>> updateRespuesta(@RequestBody Respuesta respuesta) {
        if (respuesta.getId() == null) return ResponseEntity.badRequest().build();

        List<RespuestaDTO> respuestas = respuestaService.update(respuesta);
        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }

    @DeleteMapping("/foro/respuestas/{id}")
    private ResponseEntity<List<RespuestaDTO>> deleteRespuesta(@PathVariable Long respuestaId) {
        Long userId = 11L;
        List<RespuestaDTO> respuestas = respuestaService.deleteOne(respuestaId, userId);
        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }
}
