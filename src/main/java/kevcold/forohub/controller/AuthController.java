package kevcold.forohub.controller;

import kevcold.forohub.infra.auth.AuthRequest;
import kevcold.forohub.infra.auth.AuthResponse;
import kevcold.forohub.infra.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            String token = authService.authenticate(authRequest);
            AuthResponse authResponse = new AuthResponse(token, 3600L); // assuming 1 hour expiry
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el servidor: " + e.getMessage());
        }
    }
}
