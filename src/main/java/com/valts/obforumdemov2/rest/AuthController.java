package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.RegistrationDTO;
import com.valts.obforumdemov2.dto.UserAuthDTO;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.security.JwtUtil;
import com.valts.obforumdemov2.services.implementations.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;


    @PostMapping("/foro/auth/register")
    public ResponseEntity<String> registrate(@RequestBody RegistrationDTO registrationDTO) {

        Optional<User> userOptional = userRepository.findByEmail(registrationDTO.getEmail());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(409).body("¡Email ya esta registrado!");
        }

        String successMessage =  registrationService.signUpUser(registrationDTO);
        return ResponseEntity.ok(successMessage);
    }

    @PostMapping("/foro/auth/login")
    public ResponseEntity<String> login(@RequestBody UserAuthDTO userAuthDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDTO.getEmail(), userAuthDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        return ResponseEntity.ok(jwt);
    }

}
