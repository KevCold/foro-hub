package kevcold.forohub.controller;

import jakarta.validation.Valid;
import kevcold.forohub.domain.topico.DatosActualizarTopico;
import kevcold.forohub.domain.topico.DatosListadoTopicos;
import kevcold.forohub.domain.topico.service.TopicoService;
import kevcold.forohub.domain.topico.DatosRegistroTopico;
import kevcold.forohub.domain.topico.RegistroTopicoRespuestaDTO;
import kevcold.forohub.infra.errors.DuplicateResourceException;
import kevcold.forohub.infra.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            return ResponseEntity.status(409).body(new RegistroTopicoRespuestaDTO(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new RegistroTopicoRespuestaDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RegistroTopicoRespuestaDTO("Error al registrar el tópico: " + e.getMessage()));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DatosListadoTopicos>> listarTopicos(
            @PageableDefault(size = 5, sort = "fechaCreacion") Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listarTopicos(paginacion));
    }

    @PutMapping
    public ResponseEntity<RegistroTopicoRespuestaDTO> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        try {
            RegistroTopicoRespuestaDTO respuesta = topicoService.actualizarTopico(datosActualizarTopico);
            return ResponseEntity.ok(respuesta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new RegistroTopicoRespuestaDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RegistroTopicoRespuestaDTO("Error al actualizar el tópico: " + e.getMessage()));
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<RegistroTopicoRespuestaDTO> obtenerDatosTopicoPorId(@PathVariable Long id) {
        try {
            RegistroTopicoRespuestaDTO respuesta = topicoService.obtenerDatosTopicoPorId(id);
            return ResponseEntity.ok(respuesta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new RegistroTopicoRespuestaDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RegistroTopicoRespuestaDTO("Error al obtener los datos del tópico: " + e.getMessage()));
        }
    }
    @DeleteMapping("/logico/{id}")
    public ResponseEntity<?> eliminarLogicamenteTopico(@PathVariable Long id) {
        try {
            topicoService.borrarTopicoLogico(id);
            return ResponseEntity.ok().body("Tópico eliminado lógicamente con éxito.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar lógicamente el tópico: " + e.getMessage());
        }
    }

    @DeleteMapping("/fisico/{id}")
    public ResponseEntity<?> eliminarFisicamenteTopico(@PathVariable Long id) {
        try {
            topicoService.borrarTopicoPermanente(id);
            return ResponseEntity.ok().body("Tópico eliminado permanentemente con éxito.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar permanentemente el tópico: " + e.getMessage());
        }
    }

}
