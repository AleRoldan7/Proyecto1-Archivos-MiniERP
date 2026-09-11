package backend.proyecto1archivosminierp.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private Long idUsuario;
    private String username;
    private String rol;
    private String mensaje;
    //private String token;
}
