package kevcold.forohub.infra.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El campo correo electronico no puede estar vacio")
        String correoElectronico,

        @NotBlank(message = "El campo contraseña no puede estar vacio")
        String contrasena
) {
}
