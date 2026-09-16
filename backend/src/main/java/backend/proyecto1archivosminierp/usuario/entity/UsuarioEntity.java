package backend.proyecto1archivosminierp.usuario.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    //@Column(nullable = false)
    private String nombreUsuario;

    //@Column(nullable = false)
    private String correoUsuario;

    //@Column(nullable = false)
    private String telefonoUsuario;

    //@Column(nullable = false)
    private boolean estadoUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rolEntity;

    //@Column(nullable = false, unique = true)
    private String userName;

    //@Column(nullable = false)
    private String password;
}
