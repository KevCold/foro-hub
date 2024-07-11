package kevcold.forohub.domain.users;

import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import kevcold.forohub.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public RegistroUsuarioRespuestaDTO registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        String encodedPassword = passwordEncoder.encode(registroUsuarioDTO.contrasena());
        Usuario usuario = new Usuario(registroUsuarioDTO.nombre(), registroUsuarioDTO.correoElectronico(), encodedPassword);
        try {
            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return new RegistroUsuarioRespuestaDTO(usuarioGuardado.getId(), usuarioGuardado.getNombre(), usuarioGuardado.getCorreoElectronico(), usuarioGuardado.getContrasena());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("El usuario con el correo " + registroUsuarioDTO.correoElectronico() + " ya está registrado.");
        }
    }

    public Usuario findUsuarioByCorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el correo: " + correoElectronico));
    }
}
