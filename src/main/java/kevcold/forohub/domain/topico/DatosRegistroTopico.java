package kevcold.forohub.domain.topico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kevcold.forohub.domain.curso.DatosCurso;
import kevcold.forohub.domain.users.DatosUsuario;

import java.time.LocalDateTime;

public record DatosRegistroTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        LocalDateTime fechaCreacion,
        @NotNull
        StatusTopicos statusTopicos,
        @NotNull
        @Valid
        DatosUsuario autor,
        @NotNull
        @Valid
        DatosCurso curso) {
}
