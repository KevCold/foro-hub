package kevcold.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuestas(
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotNull(message = "El ID del tópico es obligatorio")
        Long topicoId,

        @NotNull(message = "El ID del autor es obligatorio")
        Long autorId
) {}
