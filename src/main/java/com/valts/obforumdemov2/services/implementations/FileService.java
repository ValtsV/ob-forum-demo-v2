package com.valts.obforumdemov2.services.implementations;

import com.sun.tools.jconsole.JConsoleContext;
import com.valts.obforumdemov2.bucket.BucketName;
import com.valts.obforumdemov2.filestore.FileStore;
import com.valts.obforumdemov2.models.Curso;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.CursoRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class FileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    FileStore fileStore;

    public void uploadUserProfileImage(Long userId, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (user -> avatar) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        String newFileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, newFileName, user.getAvatar(), Optional.of(metadata), file.getInputStream());
            user.setAvatar(newFileName);
            userRepository.save(user);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public byte[] downloadUserProfileImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getId());

        return fileStore.download(path, user.getAvatar());

    }

    public byte[] downloadCursoProfileImage(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalStateException(String.format("Curso with id %s not found", cursoId)));

        String path = String.format("%s",
                BucketName.PROFILE_IMAGE.getBucketName());

        return fileStore.download(path, curso.getAvatar());

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
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
