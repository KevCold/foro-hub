package kevcold.forohub.domain.topico;

import kevcold.forohub.domain.curso.DatosCurso;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopicos statusTopicos,
        DatosCurso curso
) {
}
