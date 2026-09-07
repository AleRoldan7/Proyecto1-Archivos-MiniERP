package backend.proyecto1archivosminierp.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    private String userName;
    private String password;
}
