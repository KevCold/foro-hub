package kevcold.forohub.controller;

import jakarta.validation.Valid;
import kevcold.forohub.domain.users.service.UsuarioService;
import kevcold.forohub.domain.users.RegistroUsuarioDTO;
import kevcold.forohub.domain.users.RegistroUsuarioRespuestaDTO;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO registroUsuarioDTO) {
        try {
            RegistroUsuarioRespuestaDTO respuesta = usuarioService.registrarUsuario(registroUsuarioDTO);
            return ResponseEntity.status(201).body(respuesta);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el usuario: " + e.getMessage()); // 500 Internal Server Error with message
        }
    }
}
