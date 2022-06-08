package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.models.*;
import com.valts.obforumdemov2.repositories.PreguntaRepository;
import com.valts.obforumdemov2.repositories.RespuestaRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.repositories.VotoRepository;
import com.valts.obforumdemov2.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<VotoRespuesta> saveRespuesta(Long userId, Voto vote, Long voteTypeId) {
// TODO: delete vote if user votes for opposite

            Optional<Respuesta> respuestaOptional = respuestaRepository.findById(voteTypeId);
            if (respuestaOptional.isEmpty()) {
                return votoRepository.findAllByRespuesta_Id(voteTypeId);
            };

            Optional<VotoRespuesta> votoRespuestaOptional = votoRepository.findByRespuestaIdAndUserIdAndVote(voteTypeId, userId, vote.isVote());
            if (votoRespuestaOptional.isPresent()) {
                votoRepository.delete(votoRespuestaOptional.get());
                return votoRepository.findAllByRespuesta_Id(voteTypeId);
//                return "Vote deleted";
            }

            Optional<VotoRespuesta> votoRespuestaOptionalWithSwappedVote = votoRepository.findByRespuestaIdAndUserIdAndVote(voteTypeId, userId, !vote.isVote());
            if (votoRespuestaOptionalWithSwappedVote.isPresent()) {
                votoRepository.delete(votoRespuestaOptionalWithSwappedVote.get());
            }

            VotoRespuesta votoRespuesta = (VotoRespuesta) vote;
            votoRespuesta.setRespuesta(respuestaOptional.get());
            votoRespuesta.setUser(userRepository.getById(userId));
            votoRepository.save(votoRespuesta);
            return votoRepository.findAllByRespuesta_Id(voteTypeId);
    }

    public List<VotoPregunta> savePregunta(Long userId, Voto vote, Long voteTypeId) {

        Optional<Pregunta> preguntaOptional = preguntaRespository.findById(voteTypeId);
        if (preguntaOptional.isEmpty()) return votoRepository.findAllByPregunta_Id(voteTypeId);

        Optional<VotoPregunta> votoPreguntaOptional = votoRepository.findByPreguntaIdAndUserIdAndVote(voteTypeId, userId, vote.isVote());
        if (votoPreguntaOptional.isPresent()) {
            votoRepository.delete(votoPreguntaOptional.get());
            return votoRepository.findAllByPregunta_Id(voteTypeId);
        }

        Optional<VotoPregunta> votoPreguntaOptionalWithSwappedVote = votoRepository.findByPreguntaIdAndUserIdAndVote(voteTypeId, userId, !vote.isVote());
        if (votoPreguntaOptionalWithSwappedVote.isPresent()) {
            votoRepository.delete(votoPreguntaOptionalWithSwappedVote.get());
        }

        VotoPregunta votoPregunta = (VotoPregunta) vote;
        votoPregunta.setPregunta(preguntaOptional.get());
        votoPregunta.setUser(userRepository.getById(userId));
        votoRepository.save(votoPregunta);
        return votoRepository.findAllByPregunta_Id(voteTypeId);

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
