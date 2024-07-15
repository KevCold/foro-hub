package kevcold.forohub.domain.respuesta.repository;

import kevcold.forohub.domain.respuesta.Respuesta;
import kevcold.forohub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopicoAndActivoTrue(Topico topico);
}
