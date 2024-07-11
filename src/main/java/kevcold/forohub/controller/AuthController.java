package kevcold.forohub.controller;

import jakarta.validation.Valid;
import kevcold.forohub.infra.auth.AuthRequest;
import kevcold.forohub.infra.auth.AuthResponse;
import kevcold.forohub.infra.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${api.security.jwt.expiration}")
    private Long jwtExpirationMs;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest.username(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(token, jwtExpirationMs));
    }


}
