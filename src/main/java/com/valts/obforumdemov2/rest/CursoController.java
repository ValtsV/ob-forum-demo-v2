package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.CursoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CursoController {

    private final CursoServiceImpl cursoService;

    @PostMapping("/foro/curso/{cursoId}/followers")
    private ResponseEntity<Void> followCurso(@PathVariable Long cursoId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        cursoService.followCurso(userDetails.getId(), cursoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/foro/curso/{cursoId}/followers")
    private ResponseEntity<Void> unfollowCurso(@PathVariable Long cursoId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        cursoService.unfollowCurso(userDetails.getId(), cursoId);
        return ResponseEntity.ok().build();
    }
}
