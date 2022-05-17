package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.models.*;
import com.valts.obforumdemov2.repositories.PreguntaRepository;
import com.valts.obforumdemov2.repositories.RespuestaRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.repositories.VotoRepository;
import com.valts.obforumdemov2.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotosServiceImpl implements VotoService {

    @Autowired
    VotoRepository votoRepository;

    @Autowired
    PreguntaRepository preguntaRespository;

    @Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
    UserRepository userRepository;

//    boolean isVoted = votosService.save(userId, vote, respuestaId, "respuesta");
//    saves vote with respuesta or pregunta id depending on which parameter is passed
    public boolean save(Long userId, Voto vote, Long voteTypeId, String voteType) {
        if (voteType.equalsIgnoreCase("respuesta")) {
            Optional<Respuesta> respuestaOptional = respuestaRepository.findById(voteTypeId);
            if (respuestaOptional.isEmpty()) return false;

            VotoRespuesta votoRespuesta = (VotoRespuesta) vote;
            votoRespuesta.setRespuesta(respuestaOptional.get());
            votoRespuesta.setUser(userRepository.getById(userId));
            votoRepository.save(votoRespuesta);
            return true;
        }

        if (voteType.equalsIgnoreCase("pregunta")) {
            Optional<Pregunta> preguntaOptional = preguntaRespository.findById(voteTypeId);
            if (preguntaOptional.isEmpty()) return false;

            VotoPregunta votoPregunta = (VotoPregunta) vote;
            votoPregunta.setPregunta(preguntaOptional.get());
            votoPregunta.setUser(userRepository.getById(userId));
            votoRepository.save(votoPregunta);
            return true;
        }

        return false;
    }

//    boolean isVoted = votosService.update(userId, respuestaId, "respuesta")
//    updates vote
    public boolean update(Long userId, Long voteTypeId, String voteType) {
        if (voteType.equalsIgnoreCase("respuesta")) {
            Optional<VotoRespuesta> votoRespuestaOptional = votoRepository.findByRespuestaIdAndUserId(voteTypeId, userId);
            if (votoRespuestaOptional.isEmpty()) return false;

            VotoRespuesta votoRespuesta = votoRespuestaOptional.get();
            votoRespuesta.setVote(!votoRespuesta.isVote());
            votoRepository.save(votoRespuesta);
            return true;
        }

        if (voteType.equalsIgnoreCase("pregunta")) {
            Optional<VotoPregunta> votoPreguntaOptional = votoRepository.findByPreguntaIdAndUserId(voteTypeId, userId);
            if (votoPreguntaOptional.isEmpty()) return false;

            VotoPregunta votoPregunta = votoPreguntaOptional.get();
            votoPregunta.setVote(!votoPregunta.isVote());
            votoRepository.save(votoPregunta);
            return true;
        }

        return false;
    }

//    boolean isDeleted = votosService.delete(userId, respuestaId, "respuesta");
//    deletes voto
    public boolean delete(Long userId, Long voteTypeId, String voteType) {
        if (voteType.equalsIgnoreCase("respuesta")) {
            Optional<VotoRespuesta> votoRespuestaOptional = votoRepository.findByRespuestaIdAndUserId(voteTypeId, userId);
            if (votoRespuestaOptional.isEmpty()) return false;

            votoRepository.deleteById(votoRespuestaOptional.get().getId());
            return true;
        }

        if (voteType.equalsIgnoreCase("pregunta")) {
            Optional<VotoPregunta> votoPreguntaOptional = votoRepository.findByPreguntaIdAndUserId(voteTypeId, userId);
            if (votoPreguntaOptional.isEmpty()) return false;

            votoRepository.deleteById(votoPreguntaOptional.get().getId());
            return true;
        }

        return false;
    }
}
