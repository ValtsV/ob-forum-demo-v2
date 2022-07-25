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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
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
    private ResponseEntity<PreguntaUserVoteDTO> addPregunta(@RequestBody Pregunta pregunta, Authentication authentication) {
        if(pregunta.getTemaId() == null) return ResponseEntity.badRequest().build();

        User userDetails = (User) authentication.getPrincipal();
        PreguntaUserVoteDTO savedPregunta = preguntaService.save(pregunta, userDetails.getId());
        if (savedPregunta == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(savedPregunta);
    }

    @PutMapping("/foro/preguntas")
    private ResponseEntity<PreguntaUserVoteDTO> updatePregunta(@RequestBody Pregunta pregunta, Authentication authentication, HttpServletRequest request) throws IncorrectUserException {
        if (pregunta.getId() == null) return ResponseEntity.badRequest().build();

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        PreguntaUserVoteDTO updatedPregunta = preguntaService.update(pregunta, (User) authentication.getPrincipal(), isAdmin);
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

    @PostMapping("/foro/preguntas/{preguntaId}/followers")
    private ResponseEntity<Void> followPregunta(@PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        preguntaService.followPregunta(userDetails.getId(), preguntaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/foro/preguntas/{preguntaId}/followers")
    private ResponseEntity<Void> unfollowPregunta(@PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        preguntaService.unfollowPregunta(userDetails.getId(), preguntaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/foro/preguntas/{preguntaId}/followers")
    private ResponseEntity<Boolean> checkFollowStatus(@PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();

        Boolean isFollowing = preguntaService.checkFollowStatus(preguntaId, userDetails.getId());
        return ResponseEntity.ok(isFollowing);
    }
}
