package kevcold.forohub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kevcold.forohub.domain.respuesta.DatosActualizarRespuestas;
import kevcold.forohub.domain.respuesta.DatosRegistroRespuestas;
import kevcold.forohub.domain.respuesta.DatosRespuesta;
import kevcold.forohub.domain.respuesta.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping("/registrar")
    public ResponseEntity<DatosRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuestas datosRegistroRespuestas) {
        DatosRespuesta respuesta = respuestaService.registrarRespuesta(datosRegistroRespuestas);
        return ResponseEntity.status(201).body(respuesta);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<DatosRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuestas datosActualizarRespuestas) {
        DatosRespuesta respuesta = respuestaService.actualizarRespuesta(datosActualizarRespuestas);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<DatosRespuesta> obtenerRespuestaPorId(@PathVariable Long id) {
        DatosRespuesta respuesta = respuestaService.obtenerRespuestaPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/listarPorTopico/{topicoId}")
    public ResponseEntity<List<DatosRespuesta>> listarRespuestasPorTopico(@PathVariable Long topicoId) {
        List<DatosRespuesta> respuestas = respuestaService.listarRespuestasPorTopico(topicoId);
        return ResponseEntity.ok(respuestas);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRespuestaLogica(@PathVariable Long id) {
        respuestaService.eliminarRespuestaLogica(id);
        return ResponseEntity.ok().body("Respuesta eliminada lógicamente con éxito.");
    }

    @DeleteMapping("/baja/{id}")
    public ResponseEntity<?> eliminarRespuestaPermanente(@PathVariable Long id) {
        respuestaService.eliminarRespuestaPermanente(id);
        return ResponseEntity.ok().body("Respuesta eliminada permanentemente con éxito.");
    }

    @PutMapping("/marcarComoSolucion/{id}")
    public ResponseEntity<DatosRespuesta> marcarComoSolucion(@PathVariable Long id) {
        DatosRespuesta respuesta = respuestaService.marcarComoSolucion(id);
        return ResponseEntity.ok(respuesta);
    }
}
