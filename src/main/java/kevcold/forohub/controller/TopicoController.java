package kevcold.forohub.controller;

import jakarta.validation.Valid;
import kevcold.forohub.domain.topico.TopicoService;
import kevcold.forohub.domain.topico.DatosRegistroTopico;
import kevcold.forohub.domain.topico.RegistroTopicoRespuestaDTO;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<RegistroTopicoRespuestaDTO> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String correoElectronico = authentication.getName();

            RegistroTopicoRespuestaDTO respuesta = topicoService.registrarTopico(datosRegistroTopico, correoElectronico);
            return ResponseEntity.status(201).body(respuesta);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(409).body(new RegistroTopicoRespuestaDTO(null, null, null, e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new RegistroTopicoRespuestaDTO(null, null, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RegistroTopicoRespuestaDTO(null, null, null, "Error al registrar el tópico: " + e.getMessage()));
        }
    }
}
