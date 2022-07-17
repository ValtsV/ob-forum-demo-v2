package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.RespuestaDTO;
import com.valts.obforumdemov2.models.Respuesta;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.RespuestaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class RespuestasController {

    private final RespuestaServiceImpl respuestaService;

    @GetMapping("/foro/respuestas/preguntas/{preguntaId}")
    private ResponseEntity<List<RespuestaDTO>> getRespuestasByPreguntaId(@PathVariable Long preguntaId, Authentication authentication) {
        if (preguntaId == null) return ResponseEntity.badRequest().build();

        User userDetails = (User) authentication.getPrincipal();
        List<RespuestaDTO> respuestas = respuestaService.findByPreguntaId(preguntaId, userDetails.getId());
        if (respuestas.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(respuestas);
    }

    @PostMapping("/foro/respuestas")
    private ResponseEntity<List<RespuestaDTO>> addRespuesta(@RequestBody Respuesta respuesta, Authentication authentication){
        if (respuesta.getPreguntaId() == null) return ResponseEntity.badRequest().build();

        User userDetails = (User) authentication.getPrincipal();
        List<RespuestaDTO> respuestas = respuestaService.save(respuesta, userDetails.getId());

        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }

    @PutMapping("/foro/respuestas")
    private ResponseEntity<List<RespuestaDTO>> updateRespuesta(@RequestBody Respuesta respuesta, Authentication authentication) {
        if (respuesta.getId() == null) return ResponseEntity.badRequest().build();

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        List<RespuestaDTO> respuestas = respuestaService.update(respuesta, (User) authentication.getPrincipal(), isAdmin);
        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }

    @DeleteMapping("/foro/respuestas/{id}")
    private ResponseEntity<List<RespuestaDTO>> deleteRespuesta(@PathVariable Long respuestaId, Authentication authentication) {
        List<RespuestaDTO> respuestas = respuestaService.deleteOne(respuestaId, (User) authentication.getPrincipal());
        if (respuestas == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(respuestas);
    }
}
