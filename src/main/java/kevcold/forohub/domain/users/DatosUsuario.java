package kevcold.forohub.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosUsuario(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String correoElectronico,
        @NotBlank
        @Pattern(regexp = "\\d{6,255}")
        String constrasena
) {
}
