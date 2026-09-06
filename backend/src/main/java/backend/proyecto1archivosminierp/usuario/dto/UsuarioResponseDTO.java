package backend.proyecto1archivosminierp.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String correoUsuario;
    private String telefonoUsuario;
    private boolean estadoUsuario;
    private Long idRol;
    private String nombreRol;
    private String userName;
}
