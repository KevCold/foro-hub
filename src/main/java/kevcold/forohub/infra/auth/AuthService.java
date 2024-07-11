package kevcold.forohub.infra.auth;

import kevcold.forohub.infra.configuration.JwtUtils;
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

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtil,
                       UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public String authenticate(String username, String password) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("User {} successfully authenticated", username);
            return token;
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for user {}: Bad credentials", username);
            throw new BadCredentialsException("Invalid username or password");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user {}", username, e);
            throw e;
        }
    }
}