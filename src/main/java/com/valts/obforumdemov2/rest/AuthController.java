package com.valts.obforumdemov2.rest;

import com.valts.obforumdemov2.dto.*;
import com.valts.obforumdemov2.exceptions.TokenRefreshException;
import com.valts.obforumdemov2.models.RefreshToken;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.security.JwtUtil;
import com.valts.obforumdemov2.services.implementations.RefreshTokenService;
import com.valts.obforumdemov2.services.implementations.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

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
    public ResponseEntity<?> login(@RequestBody UserAuthDTO userAuthDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDTO.getEmail(), userAuthDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = jwtUtil.generateJwtCookie((User) authentication.getPrincipal());

        User userDetails = (User) authentication.getPrincipal();

        List<String> roles = userDetails.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());

        ResponseCookie refreshCookie = refreshTokenService.createRefreshToken(userDetails.getId());

        LoginResponse loginResponse = new LoginResponse(userDetails.getId(), userDetails.getAvatar(), userDetails.getUsername(), roles);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(loginResponse);
    }

    @GetMapping("foro/auth/refreshtoken")
    public ResponseEntity<?> refreshtoken(@CookieValue(name = "refreshToken") String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(user);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(true);
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
    }

}
