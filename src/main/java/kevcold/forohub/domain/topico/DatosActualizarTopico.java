package kevcold.forohub.domain.topico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kevcold.forohub.domain.curso.DatosCurso;

public record DatosActualizarTopico(
        @NotNull Long id,
        String mensaje,
        StatusTopicos status,
        @Valid DatosCurso curso
) {}
