package kevcold.forohub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.domain.curso.DatosCurso;
import kevcold.forohub.domain.topico.DatosRegistroTopico;
import kevcold.forohub.domain.topico.DatosRespuestaTopico;
import kevcold.forohub.domain.topico.Topico;
import kevcold.forohub.repository.ITopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private ITopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                          UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatusTopicos(), new DatosCurso(topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }
}
