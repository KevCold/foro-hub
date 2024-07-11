package kevcold.forohub.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String correoElectronico,

        @NotBlank
        @Size(min = 6, max = 255)
        String contrasena) {

}

