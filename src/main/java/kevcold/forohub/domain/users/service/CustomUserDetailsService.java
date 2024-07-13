package kevcold.forohub.domain.users.service;

import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.domain.users.repository.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Intentando cargar usuario por Username", username);

        Usuario usuario = usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> {
                    logger.warn("No se encontro el usuario con ese correo", username);
                    return new UsernameNotFoundException("No se encontro el usuario con ese correo " + username);
                });

        logger.debug("User found: {}", username);

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreoElectronico())
                .password(usuario.getContrasena())
                .authorities(Collections.emptyList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}