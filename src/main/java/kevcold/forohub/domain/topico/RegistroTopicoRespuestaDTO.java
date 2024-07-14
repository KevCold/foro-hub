package kevcold.forohub.domain.topico;

public record RegistroTopicoRespuestaDTO(
        Long id,
        Long cursoId,
        Long autorId,
        String mensajeExito
) {
    public RegistroTopicoRespuestaDTO(Long id, Long cursoId, Long autorId) {
        this(id, cursoId, autorId, "Operación exitosa");
    }

    public RegistroTopicoRespuestaDTO(String mensajeExito) {
        this(null, null, null, mensajeExito);
    }
}
