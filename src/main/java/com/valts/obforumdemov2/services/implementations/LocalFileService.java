package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.filestore.FileStore;
import com.valts.obforumdemov2.models.Curso;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.CursoRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class LocalFileService {

    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    FileStore fileStore;

    public void uploadUserProfileImage(Long userId, MultipartFile file) throws IOException {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

        File dir = new File(DIRECTORY + user.getId());
        if (!dir.exists()) dir.mkdirs();
        Path oldFilePath = null;
        if (user.getAvatar() != null) {
            oldFilePath = get(DIRECTORY + user.getId(), user.getAvatar()).toAbsolutePath().normalize();
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = get(DIRECTORY + user.getId(), filename).toAbsolutePath().normalize();

        try {
            copy(file.getInputStream(), filePath, REPLACE_EXISTING);
            if (oldFilePath != null) deleteIfExists(oldFilePath);

        } catch (IOException e) {
            throw new IOException(e);
        }

        user.setAvatar(filename);
        userRepository.save(user);
    }

    public byte[] downloadUserProfileImage(Long userId) throws FileNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));
        if (user.getAvatar() == null) return null;

        Path filePath = get(DIRECTORY + user.getId(), user.getAvatar()).toAbsolutePath().normalize();
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(user.getAvatar() + " was not found on the server");
        }
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public byte[] downloadCursoProfileImage(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalStateException(String.format("Curso with id %s not found", cursoId)));
        Path filePath = get(DIRECTORY + "/cursos" + curso.getId(), curso.getAvatar()).toAbsolutePath().normalize();

        return null;
    }

    public void uploadCursoProfileImage(Long userId, MultipartFile file) throws IOException {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        Curso curso = cursoRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

        File dir = new File(DIRECTORY + "/cursos" + curso.getId());
        if (!dir.exists()) dir.mkdirs();
        Path oldFilePath = null;
        if (curso.getAvatar() != null) {
            oldFilePath = get(DIRECTORY + curso.getId(), curso.getAvatar()).toAbsolutePath().normalize();
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = get(DIRECTORY + curso.getId(), filename).toAbsolutePath().normalize();

        try {
            copy(file.getInputStream(), filePath, REPLACE_EXISTING);
            if (oldFilePath != null) deleteIfExists(oldFilePath);

        } catch (IOException e) {
            throw new IOException(e);
        }

        curso.setAvatar(filename);
        cursoRepository.save(curso);
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
}
