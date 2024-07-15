package kevcold.forohub.domain.topico;

import jakarta.persistence.*;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.domain.respuesta.Respuesta;
import kevcold.forohub.domain.users.Usuario;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "topico", uniqueConstraints = {@UniqueConstraint(columnNames = "titulo")})
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Lob
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private StatusTopicos statusTopicos;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    private boolean activo;

    public Topico(DatosRegistroTopico datosRegistroTopico){
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = datosRegistroTopico.fechaCreacion();
        this.statusTopicos = datosRegistroTopico.statusTopicos();
        this.curso = new Curso(datosRegistroTopico.curso());
        this.activo = true;
    }

    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.status() != null) {
            this.statusTopicos = datosActualizarTopico.status();
        }
        if (datosActualizarTopico.curso() != null) {
            this.curso.actualizarCurso(datosActualizarTopico.curso());
        }
    }
}
