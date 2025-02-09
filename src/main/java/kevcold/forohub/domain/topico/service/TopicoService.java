package kevcold.forohub.domain.topico.service;

import kevcold.forohub.domain.topico.*;
import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import kevcold.forohub.infra.errors.UnauthorizedException;
import kevcold.forohub.domain.topico.repository.ITopicoRepository;
import kevcold.forohub.domain.users.repository.IUsuarioRepository;
import kevcold.forohub.domain.curso.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                    curso.getId(),
                    autor.getId(),
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
                topico.getCurso().getId(),
                topico.getAutor().getId(),
                "Tópico actualizado exitosamente"
        );
    }

    @Transactional
    public RegistroTopicoRespuestaDTO obtenerDatosTopicoPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        return new RegistroTopicoRespuestaDTO(
                topico.getId(),
                topico.getCurso().getId(),
                topico.getAutor().getId(),
                "Operación exitosa"
        );
    }

    @Transactional
    public void borrarTopicoLogico(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoElectronico = authentication.getName();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));

        boolean esAdminOModerador = usuarioActual.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("MODERATOR"));

        if (!usuarioActual.getId().equals(topico.getAutor().getId()) && !esAdminOModerador) {
            throw new UnauthorizedException("No tienes permiso para eliminar este tópico.");
        }

        topico.setActivo(false);
        topicoRepository.save(topico);
    }

    @Transactional
    public void borrarTopicoPermanente(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoElectronico = authentication.getName();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));

        boolean esAdminOModerador = usuarioActual.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("MODERATOR"));

        if (!usuarioActual.getId().equals(topico.getAutor().getId()) && !esAdminOModerador) {
            throw new UnauthorizedException("No tienes permiso para eliminar este tópico.");
        }

        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico no encontrado");
        }

        topicoRepository.deleteById(id);
    }
}
