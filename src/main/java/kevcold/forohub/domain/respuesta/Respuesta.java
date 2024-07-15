package kevcold.forohub.domain.respuesta;

import jakarta.persistence.*;
import kevcold.forohub.domain.topico.Topico;
import kevcold.forohub.domain.users.Usuario;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    private boolean activo;

    private boolean solucion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        this.solucion = false;
    }
}
