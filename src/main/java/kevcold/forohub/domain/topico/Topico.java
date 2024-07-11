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

    @Column(nullable = false)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTopicos statusTopicos;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private Set<Respuesta> respuestas;

    public Topico(DatosRegistroTopico datosRegistroTopico){
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = datosRegistroTopico.fechaCreacion();
        this.statusTopicos = datosRegistroTopico.statusTopicos();
        this.curso = new Curso(datosRegistroTopico.curso());
    }
}
