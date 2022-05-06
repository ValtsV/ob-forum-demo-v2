package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.dto.PreguntaDTO;
import com.valts.obforumdemov2.dto.PreguntaUserVoteDTO;
import com.valts.obforumdemov2.models.Pregunta;
import com.valts.obforumdemov2.models.Tema;
import com.valts.obforumdemov2.repositories.PreguntaRespository;
import com.valts.obforumdemov2.repositories.TemaRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    PreguntaRespository preguntaRespository;

    @Autowired
    TemaRepository temaRepository;

    @Autowired
    UserRepository userRepository;

//    List<Pregunta> preguntas = preguntaService.findByTemaId(temaId);
    //finds preguntas filtered by tema id
    public List<PreguntaDTO> findByTemaId(Long id) {
        return preguntaRespository.findAllByTemaId(id);
    }

//    PreguntaDTO pregunta = preguntaService.findById(id)
//    finds pregunta by its id
    public PreguntaUserVoteDTO findById(Long preguntaId, Long userId) {
        Optional<PreguntaUserVoteDTO> preguntaDTOOptional = preguntaRespository.findPreguntaById(preguntaId, userId);

        if (preguntaDTOOptional.isEmpty()) return null;

        return preguntaDTOOptional.get();
    }

//     Pregunta pregunta = preguntaService.save(pregunta);
//    saves pregunta in db

    public Pregunta save(Pregunta pregunta, Long userId) {
        Optional<Tema> temaOptional = temaRepository.findById(pregunta.getTemaId());
        if (temaOptional.isEmpty()) return null;

        pregunta.setTema(temaOptional.get());
        pregunta.setUser(userRepository.getById(userId));
        pregunta.setUpdatedAt(LocalDateTime.now());
        return preguntaRespository.save(pregunta);
    }

//     Pregunta updatedPregunta = preguntaService.update(pregunta);
//    updates pregunta
    public Pregunta update(Pregunta pregunta) {
        Optional<Pregunta> preguntaOptional = preguntaRespository.findById(pregunta.getId());
        if(preguntaOptional.isEmpty()) return null;

        Pregunta preguntaToUpdate = preguntaOptional.get();
        preguntaToUpdate.setTitle(pregunta.getTitle());
        preguntaToUpdate.setDescription(pregunta.getDescription());
//        TODO: check admin privileges for setting ispinned
        preguntaToUpdate.setPinned(pregunta.isPinned());
        preguntaToUpdate.setUpdatedAt(LocalDateTime.now());

        return preguntaRespository.save(preguntaToUpdate);
    }

//      boolean isDeleted = preguntaService.deleteOne(id);
//    deletes pregunta
    public boolean deleteOne(Long id) {
        boolean exists = preguntaRespository.existsById(id);

        if(!exists) return false;

        preguntaRespository.deleteById(id);
        return true;
    }
}
