package kevcold.forohub.domain.topico;

public record RegistroTopicoRespuestaDTO(
        Long topicoId,
        Long autorId,
        Long cursoId,
        String mensaje
) {
    // Constructor principal
    public RegistroTopicoRespuestaDTO(Long topicoId, Long autorId, Long cursoId, String mensaje) {
        this.topicoId = topicoId;
        this.autorId = autorId;
        this.cursoId = cursoId;
        this.mensaje = mensaje;
    }
}
