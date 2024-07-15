package kevcold.forohub.domain.users;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles")
@Entity(name = "Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
