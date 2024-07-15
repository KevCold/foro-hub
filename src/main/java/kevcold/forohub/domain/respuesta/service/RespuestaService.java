package kevcold.forohub.domain.respuesta.service;

import kevcold.forohub.domain.respuesta.DatosActualizarRespuestas;
import kevcold.forohub.domain.respuesta.DatosRegistroRespuestas;
import kevcold.forohub.domain.respuesta.DatosRespuesta;
import kevcold.forohub.domain.respuesta.Respuesta;
import kevcold.forohub.domain.topico.Topico;
import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import kevcold.forohub.domain.respuesta.repository.IRespuestaRepository;
import kevcold.forohub.domain.topico.repository.ITopicoRepository;
import kevcold.forohub.domain.users.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    @Autowired
    private IRespuestaRepository respuestaRepository;

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Transactional
    public DatosRespuesta registrarRespuesta(DatosRegistroRespuestas datosRegistroRespuestas) {
        Topico topico = topicoRepository.findById(datosRegistroRespuestas.topicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));

        Usuario autor = usuarioRepository.findById(datosRegistroRespuestas.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));

        Respuesta respuesta = new Respuesta(datosRegistroRespuestas.mensaje(), topico, autor);
        return new DatosRespuesta(respuestaRepository.save(respuesta));
    }

    @Transactional
    public DatosRespuesta actualizarRespuesta(DatosActualizarRespuestas datosActualizarRespuestas) {
        Respuesta respuesta = respuestaRepository.findById(datosActualizarRespuestas.id())
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        respuesta.setMensaje(datosActualizarRespuestas.mensaje());
        return new DatosRespuesta(respuestaRepository.save(respuesta));
    }

    @Transactional
    public DatosRespuesta obtenerRespuestaPorId(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        return new DatosRespuesta(respuesta);
    }

    @Transactional
    public List<DatosRespuesta> listarRespuestasPorTopico(Long topicoId) {
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        return respuestaRepository.findByTopicoAndActivoTrue(topico)
                .stream()
                .map(DatosRespuesta::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarRespuestaLogica(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        respuesta.setActivo(false);
        respuestaRepository.save(respuesta);
    }

    @Transactional
    public void eliminarRespuestaPermanente(Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Respuesta no encontrada");
        }
        respuestaRepository.deleteById(id);
    }

    @Transactional
    public DatosRespuesta marcarComoSolucion(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        respuesta.setSolucion(true);
        return new DatosRespuesta(respuestaRepository.save(respuesta));
    }
}
