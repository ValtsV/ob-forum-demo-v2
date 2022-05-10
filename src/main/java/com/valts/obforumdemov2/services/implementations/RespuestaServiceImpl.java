package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.dto.RespuestaDTO;
import com.valts.obforumdemov2.exceptions.EntryNotFoundException;
import com.valts.obforumdemov2.exceptions.IncorrectUserException;
import com.valts.obforumdemov2.models.Pregunta;
import com.valts.obforumdemov2.models.Respuesta;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.PreguntaRespository;
import com.valts.obforumdemov2.repositories.RespuestaRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
    PreguntaRespository preguntaRespository;

    @Autowired
    UserRepository userRepository;

//    List<RespuestaDTO> respuestaService.findByPreguntaId(preguntaId)
//    returns list of respuestas filtered by pregunta id
    public List<RespuestaDTO> findByPreguntaId(Long preguntaId, Long userId) {
        return respuestaRepository.findRespuestasByPreguntaId(preguntaId, userId);
    }



//    private ResponseEntity<PreguntaRespuestasDTO> addRespuesta(Respuesta respuesta)
//    saves respuesta and returns pregunta with list of respuestas
    public List<RespuestaDTO> save(Respuesta respuesta, Long userId) {
        Optional<Pregunta> preguntaOptional = preguntaRespository.findById(respuesta.getPreguntaId());
        if (preguntaOptional.isEmpty()) return null;

        respuesta.setPregunta(preguntaOptional.get());
        respuesta.setUser(userRepository.getById(userId));
        respuesta.setUpdatedAt(LocalDateTime.now());
        respuestaRepository.save(respuesta);

        return respuestaRepository.findRespuestasByPreguntaId(respuesta.getPreguntaId(), userId);
    }

//    private ResponseEntity<PreguntaRespuestasDTO> updateRespuesta(@RequestBody Respuesta respuesta)
//    updates respuesta, returns pregunta with all respuestas
    public List<RespuestaDTO> update(Respuesta respuesta, User currentUser) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(respuesta.getId());
        if (respuestaOptional.isEmpty()) throw new EntryNotFoundException("Answer not found");

        Respuesta respuestaToUpdate = respuestaOptional.get();
        if (respuestaToUpdate.getUser().getId() != currentUser.getId() && !(currentUser.getRoles().contains("ADMIN"))) {
            throw new IncorrectUserException("Not your answer, buddy!");
        }
        respuestaToUpdate.setAnswer(respuesta.getAnswer());
        respuestaToUpdate.setPinned(respuesta.isPinned());
        respuestaToUpdate.setUpdatedAt(LocalDateTime.now());
        respuestaRepository.save(respuestaToUpdate);

        return respuestaRepository.findRespuestasByPreguntaId(respuestaToUpdate.getPreguntaId(), respuesta.getUser().getId());
    }

//    private ResponseEntity<PreguntaRespuestasDTO> deleteRespuesta(@PathVariable Long id)
//    deletes respuesta, returns pregunta with list of respuestas
//    could change to boolean, bcs frontend will have the list cached
    public List<RespuestaDTO> deleteOne(Long preguntaId, User currentUser) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(preguntaId);
        if (respuestaOptional.isEmpty()) throw new EntryNotFoundException("No such answer with id provided!");

        Respuesta respuesta = respuestaOptional.get();
        if (respuesta.getUser().getId() != currentUser.getId() && !(currentUser.getRoles().contains("ADMIN"))) {
            throw new IncorrectUserException("Not your post, buddy!");
        }

        respuestaRepository.deleteById(preguntaId);
        return respuestaRepository.findRespuestasByPreguntaId(respuestaOptional.get().getPreguntaId(), currentUser.getId());
    }
}
