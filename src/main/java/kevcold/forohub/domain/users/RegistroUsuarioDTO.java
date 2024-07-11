package kevcold.forohub.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
        @NotBlank(message = "El nombre no debe estar vacío.")
        String nombre,

        @NotBlank(message = "El correo electrónico no debe estar vacío.")
        @Email(message = "Debe proporcionar un correo electrónico válido.")
        String correoElectronico,

        @NotBlank(message = "La contraseña no debe estar vacía.")
        @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres.")
        String contrasena) {
}
