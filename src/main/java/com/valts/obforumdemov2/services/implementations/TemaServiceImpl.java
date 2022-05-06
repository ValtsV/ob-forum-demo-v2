package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.dto.TemaDTO;
import com.valts.obforumdemov2.models.Curso;
import com.valts.obforumdemov2.models.Modulo;
import com.valts.obforumdemov2.models.Tema;
import com.valts.obforumdemov2.repositories.CursoRepository;
import com.valts.obforumdemov2.repositories.ModuloRepository;
import com.valts.obforumdemov2.repositories.TemaRepository;
import com.valts.obforumdemov2.services.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaServiceImpl implements TemaService {

    @Autowired
    TemaRepository temaRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    ModuloRepository moduloRepository;

    /**
     * Finds and returns all temas(themes) filtered by curso ID(course ID) where user is enrolled in
     * @param id course ID to filter themes
     * TODO: add second param to filter by userID (after security implementation)
     * @return Returns list of TemasDTO objects
     */
    public List<TemaDTO> findByCursoId(Long id) {
        return temaRepository.findAllByCursoIdUserAccessible(id, 3L);
    }

    /**
     * Finds and returns all temas(themes) filtered by curso ID(course ID) and modulo ID(module ID) where user is enrolled in
     * @param cursoId course ID to filter themes
     * @param moduloId module ID to filter themes
     * TODO: add second param to filter by userID (after security implementation)
     * @return List of TemaDTO
     */
    public List<TemaDTO> findByCursoIdAndModuloId(Long cursoId, Long moduloId) {
        return temaRepository.findAllByCursoIdAndModuloIdUserAccessible(cursoId, moduloId, 3L);
    }

    /**
     * Finds tema(theme) by it's ID
     * @param temaId theme id to filter themes
     * @return Tema object
     */
    public Tema findById(Long temaId) {
        Optional<Tema> temaOptional = temaRepository.findById(temaId);
        if (temaOptional.isEmpty()) {
            throw new RuntimeException("Tema not found");
        }
        return temaOptional.get();
    }


    /**
     * <p>Saves tema(theme) object in database.</p>
     * Gets cursoOptional and:
     * <ul>
     *   <li>returns null if cursoOptional is empty</li>
     *   <li>saves curso in tema if cursoOptional is present</li>
     *  </ul>
     * Saves tema in database if moduloId is not found and returns saved tema object.<br>
     * If moduloId is found:
     * <ul>
     *   <li>returns null if moduloOptional is empty</li>
     *   <li>saves modulo in tema if moduloOptional is present, saves tema in database and returns tema object</li>
     * </ul>
     *
     * @param tema Tema object
     * @return Saved Tema object
     *
     */
    public Tema save(Tema tema) {
        Optional<Curso> cursoOptional = cursoRepository.findById(tema.getCursoId());
        if (cursoOptional.isEmpty()) return null;

        tema.setCurso(cursoOptional.get());

        if (tema.getModuloId() == null) return temaRepository.save(tema);

        Optional<Modulo> moduloOptional = moduloRepository.findById(tema.getModuloId());
        if (moduloOptional.isEmpty()) return null;

        tema.setModulo(moduloOptional.get());
        return temaRepository.save(tema);
    }


    /**
     * Updates Tema(Theme) entry in the database
      * @param tema Tema object with new values
     * @return Updated Tema object
     */
    public Tema update(Tema tema) {
        Optional<Tema> temaOptional = temaRepository.findById(tema.getId());
        if(temaOptional.isEmpty()) return null;

        Optional<Curso> cursoOptional = cursoRepository.findById(tema.getCursoId());
        if (cursoOptional.isEmpty()) return null;

        Tema temaToUpdate = temaOptional.get();
        temaToUpdate.setCurso(cursoOptional.get());
        temaToUpdate.setCursoId(tema.getCursoId());
        temaToUpdate.setTitle(tema.getTitle());
        temaToUpdate.setDescription(tema.getDescription());
        temaToUpdate.setPinned(tema.isPinned());

        if (tema.getModuloId() == null) {
            temaToUpdate.setModulo(null);
            temaToUpdate.setModuloId(null);
            return temaRepository.save(temaToUpdate);
        }

        Optional<Modulo> moduloOptional = moduloRepository.findById(tema.getModuloId());
        if(moduloOptional.isEmpty()) return null;

        temaToUpdate.setModulo(moduloOptional.get());
        temaToUpdate.setModuloId(tema.getModuloId());
        return temaRepository.save(temaToUpdate);
    }


    /**
     * Deletes Tema(Theme) entry from database based on Tema Id
     * @param id Id of Tema object
     * @return True if succesfully deleted, false if couldn't delete
     */
    public boolean deleteOne(Long id) {
        boolean exists = temaRepository.existsById(id);
        if(!exists) {
            return false;
        }
        temaRepository.deleteById(id);
        return true;
    }
}
