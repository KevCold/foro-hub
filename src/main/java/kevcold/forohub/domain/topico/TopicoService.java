package kevcold.forohub.domain.topico;

import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import kevcold.forohub.repository.ITopicoRepository;
import kevcold.forohub.repository.IUsuarioRepository;
import kevcold.forohub.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

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
            return new RegistroTopicoRespuestaDTO(topicoGuardado.getId(), autor.getId(), curso.getId(), "Tópico registrado exitosamente");
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("El tópico con el título " + datosRegistroTopico.titulo() + " ya está registrado.");
        }
    }
}
