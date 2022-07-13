package com.valts.obforumdemov2.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
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

    @Autowired
    AmazonS3 s3;

    public void uploadUserProfileImage(Long userId, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

        // 4. Set correct Content Type
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        // 5. Save the image in s3
        String newFileName = String.format("%s/%s-%s", user.getId(), UUID.randomUUID(), file.getOriginalFilename());
        try {
            PutObjectRequest request = new PutObjectRequest(BucketName.PROFILE_IMAGE.getBucketName(), newFileName, file.getInputStream(), metadata);
            fileStore.save(request);

            // 6. Delete old image in s3
            String[] srcParts = user.getAvatar().split("/");
            DeleteObjectRequest deleteRequest = new DeleteObjectRequest(BucketName.PROFILE_IMAGE.getBucketName(), String.format("%s/%s", srcParts[srcParts.length - 2], srcParts[srcParts.length - 1]));
            fileStore.delete(deleteRequest);
            String imgsrc = "https://" + BucketName.PROFILE_IMAGE.getBucketName() + ".s3." + s3.getRegionName() + ".amazonaws.com/" + newFileName;

            // 7. Update user avatar
            user.setAvatar(imgsrc);
            userRepository.save(user);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
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
