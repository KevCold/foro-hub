package kevcold.forohub.domain.topico.repository;

import kevcold.forohub.domain.topico.StatusTopicos;
import kevcold.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ITopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByStatusTopicosInAndActivoTrue(Set<StatusTopicos> statusTopicos, Pageable pageable);

    Page<Topico> findByActivoTrue(Pageable pageable);
}
