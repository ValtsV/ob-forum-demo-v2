package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.VotoDTO;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.models.Voto;
import com.valts.obforumdemov2.models.VotoPregunta;
import com.valts.obforumdemov2.models.VotoRespuesta;
import com.valts.obforumdemov2.services.implementations.VotosServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class VotosController {

    private final VotosServiceImpl votosService;

    @PostMapping("/foro/votos/respuestas/{respuestaId}")
    private ResponseEntity<List<VotoRespuesta>> addVotoRespuesta(@RequestBody VotoRespuesta vote, @PathVariable Long respuestaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        List<VotoRespuesta> votos = votosService.saveRespuesta(userDetails.getId(), vote, respuestaId);
//        if (result.equalsIgnoreCase("Vote saved")) return ResponseEntity.ok().build();
//        if (result.equalsIgnoreCase("Vote not saved")) return ResponseEntity.notFound().build();
//        if (result.equalsIgnoreCase("Vote deleted")) return ResponseEntity.noContent().build();
//        return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(votos);
    }

    @PostMapping(path = "/foro/votos/preguntas/{preguntaId}")
    private ResponseEntity<List<VotoPregunta>> addVotoPregunta(@RequestBody VotoPregunta vote, @PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();

        List<VotoPregunta> votos = votosService.savePregunta(userDetails.getId(), vote, preguntaId);
//        VotoDTO votos = votosService.savePregunta(userDetails.getId(), vote, preguntaId);
//        if (result.equalsIgnoreCase("Vote saved")) return ResponseEntity.ok().build();
//        if (result.equalsIgnoreCase("Vote not saved")) return ResponseEntity.notFound().build();
//        if (result.equalsIgnoreCase("Vote deleted")) return ResponseEntity.noContent().build();
//        return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(votos);

    }

    //    switches from like to dislike and vice versa
    @PutMapping("/foro/votos/respuestas/{respuestaId}")
    private ResponseEntity<Void> updateVotoRespuesta(@PathVariable Long respuestaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        boolean isVoted = votosService.update(userDetails.getId(), respuestaId, "respuesta");
        if (!isVoted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

//    switches from like to dislike and vice versa
    @PutMapping("/foro/votos/preguntas/{preguntaId}")
    private ResponseEntity<Void> updateVotoPregunta(@PathVariable Long preguntaId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        boolean isVoted = votosService.update(userDetails.getId(), preguntaId, "pregunta");
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
