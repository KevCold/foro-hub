package kevcold.forohub.controller;

import jakarta.validation.Valid;
import kevcold.forohub.domain.users.RegistroUsuarioDTO;
import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO, UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario(registroUsuarioDTO.nombre(), registroUsuarioDTO.correoElectronico(), passwordEncoder.encode(registroUsuarioDTO.contrasena()));
        usuario = usuarioRepository.save(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
