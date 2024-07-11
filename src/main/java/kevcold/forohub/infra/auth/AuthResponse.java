package kevcold.forohub.infra.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private final String token;
    private final String type = "Bearer";
    private final String tiempoExpiracion;
    private final Long expiresIn;

    public AuthResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.tiempoExpiracion = String.format("El token expira en %d hora", expiresIn / 3600);
    }
}
