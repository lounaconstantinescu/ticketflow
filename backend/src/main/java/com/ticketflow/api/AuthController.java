package com.ticketflow.api;

import com.ticketflow.api.dto.AuthRequest;
import com.ticketflow.api.dto.AuthResponse;
import com.ticketflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        String token = auth.login(req.username(), req.password());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}
