package kevcold.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        boolean activo,
        boolean solucion,
        Long autorId,
        String autorNombre,
        Long topicoId,
        String topicoTitulo
) {
    public DatosRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.isActivo(),
                respuesta.isSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
    }
}
