package kevcold.forohub.domain.respuesta;

import jakarta.persistence.*;
import kevcold.forohub.domain.topico.Topico;
import kevcold.forohub.domain.users.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean solucion;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

}
