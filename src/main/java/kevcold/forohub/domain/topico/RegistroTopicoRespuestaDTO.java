package kevcold.forohub.domain.topico;

import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.domain.users.Usuario;

public record RegistroTopicoRespuestaDTO(
        Long id,
        String titulo,
        String mensaje,
        StatusTopicos status,
        Curso curso,
        Usuario autor,
        String mensajeExito
) {
    public RegistroTopicoRespuestaDTO(Long id, String titulo, String mensaje, StatusTopicos status, Curso curso, Usuario autor) {
        this(id, titulo, mensaje, status, curso, autor, "Operación exitosa");
    }

    public RegistroTopicoRespuestaDTO(String mensajeExito) {
        this(null, null, null, null, null, null, mensajeExito);
    }
}
