package kevcold.forohub.domain.users;

import jakarta.persistence.*;
import kevcold.forohub.domain.perfil.Perfil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String correoElectronico;
    private String contrasena;

    @ManyToMany
    @JoinTable(
            name = "Usuario_Perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfiles;

    public Usuario(DatosUsuario autor) {
        this.nombre = autor.nombre();
        this.correoElectronico = autor.correoElectronico();
        this.contrasena = autor.constrasena();
    }
}
