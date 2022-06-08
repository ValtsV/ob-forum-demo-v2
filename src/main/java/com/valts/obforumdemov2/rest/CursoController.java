package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.models.Curso;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.CursoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class CursoController {

    private final CursoServiceImpl cursoService;

    @GetMapping("/foro/cursos")
    private ResponseEntity<List<Curso>> getCursos(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        List<Curso> cursos = cursoService.getCursos(userDetails);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/foro/cursos/{cursoId}")
    private ResponseEntity<Curso> getCursoById(@PathVariable Long cursoId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        Curso curso = cursoService.getCursoById(userDetails.getId(), cursoId);
        if (curso == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(curso);
    }

    @PostMapping("/foro/cursos/{cursoId}/followers")
    private ResponseEntity<Void> followCurso(@PathVariable Long cursoId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        cursoService.followCurso(userDetails.getId(), cursoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/foro/cursos/{cursoId}/followers")
    private ResponseEntity<Void> unfollowCurso(@PathVariable Long cursoId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        cursoService.unfollowCurso(userDetails.getId(), cursoId);
        return ResponseEntity.ok().build();
    }
}
