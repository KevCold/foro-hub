package kevcold.forohub.domain.topico.service;

import kevcold.forohub.domain.topico.*;
import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import kevcold.forohub.domain.topico.repository.ITopicoRepository;
import kevcold.forohub.domain.users.repository.IUsuarioRepository;
import kevcold.forohub.domain.curso.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.EnumSet;

@Service
public class TopicoService {

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ICursoRepository cursoRepository;

    @Transactional
    public RegistroTopicoRespuestaDTO registrarTopico(DatosRegistroTopico datosRegistroTopico, String correoElectronico) {
        Usuario autor = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));

        Curso curso = cursoRepository.findByNombre(datosRegistroTopico.curso().nombre())
                .orElseGet(() -> {
                    Curso nuevoCurso = new Curso();
                    nuevoCurso.setNombre(datosRegistroTopico.curso().nombre());
                    nuevoCurso.setCategoria(datosRegistroTopico.curso().categoria());
                    return cursoRepository.save(nuevoCurso);
                });

        Topico topico = new Topico(datosRegistroTopico);
        topico.setAutor(autor);
        topico.setCurso(curso);

        try {
            Topico topicoGuardado = topicoRepository.save(topico);
            return new RegistroTopicoRespuestaDTO(
                    topicoGuardado.getId(),
                    topicoGuardado.getTitulo(),
                    topicoGuardado.getMensaje(),
                    topicoGuardado.getStatusTopicos(),
                    curso,
                    autor,
                    "Tópico registrado exitosamente"
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("El tópico con el título " + datosRegistroTopico.titulo() + " ya está registrado.");
        }
    }

    @Transactional
    public Page<DatosListadoTopicos> listarTopicos(Pageable paginacion) {
        EnumSet<StatusTopicos> statusFiltrados = EnumSet.of(StatusTopicos.ABIERTO, StatusTopicos.PENDIENTE);
        return topicoRepository.findByStatusTopicosInAndActivoTrue(statusFiltrados, paginacion).map(DatosListadoTopicos::new);
    }

    @Transactional
    public RegistroTopicoRespuestaDTO actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findById(datosActualizarTopico.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        topico.actualizarTopico(datosActualizarTopico);
        return new RegistroTopicoRespuestaDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatusTopicos(),
                topico.getCurso(),
                topico.getAutor()
        );
    }

    @Transactional
    public RegistroTopicoRespuestaDTO obtenerDatosTopicoPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        return new RegistroTopicoRespuestaDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatusTopicos(),
                topico.getCurso(),
                topico.getAutor()
        );
    }

    // Método de borrado lógico
    @Transactional
    public void borrarTopicoLogico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        topico.setActivo(false);
        topicoRepository.save(topico);
    }

    // Método de borrado permanente
    @Transactional
    public void borrarTopicoPermanente(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }
}
