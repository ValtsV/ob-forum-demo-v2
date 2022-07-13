package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.services.implementations.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        User userDetails = (User) authentication.getPrincipal();
        fileService.uploadUserProfileImage(userDetails.getId(), file);
    }
}
