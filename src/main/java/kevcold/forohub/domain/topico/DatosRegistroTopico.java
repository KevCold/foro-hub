package kevcold.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kevcold.forohub.domain.curso.DatosCurso;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotNull(message = "La fecha de creación es obligatoria")
        LocalDateTime fechaCreacion,

        @NotNull(message = "El estado del tópico es obligatorio")
        StatusTopicos statusTopicos,

        @NotNull(message = "El curso es obligatorio")
        DatosCurso curso
) {
}
