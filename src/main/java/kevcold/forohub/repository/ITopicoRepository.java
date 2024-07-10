package kevcold.forohub.repository;

import kevcold.forohub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITopicoRepository extends JpaRepository<Topico, Long> {
}
