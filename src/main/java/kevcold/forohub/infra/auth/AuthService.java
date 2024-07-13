package kevcold.forohub.infra.auth;

import kevcold.forohub.domain.users.Usuario;
import kevcold.forohub.domain.users.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtil,
                       UserDetailsService userDetailsService,
                       UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.usuarioService = usuarioService;
    }

    public String authenticate(AuthRequest authRequest) throws AuthenticationException {
        String username = authRequest.correoElectronico();
        String password = authRequest.contrasena();

        try {
            // Verificar si el usuario existe
            Usuario usuario = usuarioService.findUsuarioByCorreoElectronico(username);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("Usuario {} autenticado exitosamente", username);
            return token;
        } catch (BadCredentialsException e) {
            logger.warn("La autenticación falló para el usuario {}: Credenciales incorrectas", username);
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        } catch (AuthenticationException e) {
            logger.error("La autenticación falló para el usuario {}", username, e);
            throw new AuthenticationException("Error en la autenticación para el usuario " + username, e) {};
        }
    }
}
