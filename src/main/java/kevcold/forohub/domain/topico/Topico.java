package kevcold.forohub.domain.topico;

import jakarta.persistence.*;
import kevcold.forohub.domain.curso.Curso;
import kevcold.forohub.domain.respuesta.Respuesta;
import kevcold.forohub.domain.users.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
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
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
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
    this.autor = new Usuario(datosRegistroTopico.autor());
    this.curso = new Curso(datosRegistroTopico.curso());
}

}
