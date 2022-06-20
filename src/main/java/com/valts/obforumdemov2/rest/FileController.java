package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(
            path = "foro/users/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        fileService.uploadUserProfileImage(userDetails.getId(), file);
    }

    @GetMapping("foro/users/{userId}/image")
    public ResponseEntity<byte[]> downloadUserProfileImage(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(fileService.downloadUserProfileImage(userId));
    }

//    @GetMapping(value = "foro/cursos/{cursoId}/image", produces="image/svg+xml")
//    public ResponseEntity<byte[]> downloadCursoProfileImage(@PathVariable("cursoId") Long cursoId) {
//        return ResponseEntity.ok(fileService.downloadCursoProfileImage(cursoId));
//    }

    @GetMapping("foro/cursos/{cursoId}/image")
    public ResponseEntity<byte[]> downloadCursoProfileImage(@PathVariable("cursoId") Long cursoId) {
        return ResponseEntity.ok(fileService.downloadCursoProfileImage(cursoId));
    }
}
