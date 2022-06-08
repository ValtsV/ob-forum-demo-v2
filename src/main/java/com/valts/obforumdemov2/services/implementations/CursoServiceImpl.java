package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.exceptions.AlreadyFollowingException;
import com.valts.obforumdemov2.exceptions.EntryNotFoundException;
import com.valts.obforumdemov2.models.*;
import com.valts.obforumdemov2.repositories.CursoRepository;
import com.valts.obforumdemov2.repositories.FollowerCursoRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl {

    @Autowired
    FollowerCursoRepository followerCursoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UserRepository userRepository;

    public List<Curso> getCursos(User user) {
        return cursoRepository.findAllByUser_Id(user.getId());
    }

    public Curso getCursoById(Long userId, Long cursoId) {
        return cursoRepository.findAllByUser_IdAndCursoId(userId, cursoId);
    }

    public void followCurso(Long userId, Long cursoId) {
        Optional<FollowerCurso> followerCursoOptional = followerCursoRepository.findByUserIdAndCursoId(userId, cursoId);
        if (followerCursoOptional.isPresent()) throw new AlreadyFollowingException("User already following curso " + cursoId);

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if(cursoOptional.isEmpty()) throw new EntryNotFoundException("Curso not found");

        FollowerCurso followerCurso = new FollowerCurso();
        followerCurso.setCurso(cursoOptional.get());
        followerCurso.setUser(userRepository.getById(userId));

        followerCursoRepository.save(followerCurso);
    }

    public void unfollowCurso(Long userId, Long cursoId) {
        Optional<FollowerCurso> followerCursoOptional = followerCursoRepository.findByUserIdAndCursoId(userId, cursoId);
        if (followerCursoOptional.isPresent()) throw new AlreadyFollowingException("User already following curso " + cursoId);

        followerCursoRepository.deleteById(cursoId);
    }

}
