package backend.proyecto1archivosminierp.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    private String nombreUsuario;
    private String correoUsuario;
    private String telefonoUsuario;
    private Long idRol;
    private String userName;
    private String password;

}
