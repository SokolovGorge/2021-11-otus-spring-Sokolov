package ru.otus.vacancykeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.otus.vacancykeeper.dto.ResponseDto;
import ru.otus.vacancykeeper.dto.UserDto;
import ru.otus.vacancykeeper.session.SessionRegistry;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final SessionRegistry sessionRegistry;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserDto user) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        final String sessionId = sessionRegistry.registerSession(user.getUsername());
        return ResponseEntity.ok(new ResponseDto(sessionId));
    }
}
