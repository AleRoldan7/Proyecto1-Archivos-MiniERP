package backend.proyecto1archivosminierp.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateRequestDTO {


    @NotBlank(message = "El telefono del cliente es obligatorio")
    @Size(max = 15, message = "El telefono es de máximo 15 caracteres")
    private String telefono;

    @NotBlank(message = "El correo del cliente es obligatorio")
    @Email(message = "EL correo no tiene un formato valido")
    @Size(max = 100, message = "El correo es de máximo 100 caracteres")
    private String correo;

    @NotBlank(message = "La dirección del cliente es obligatoria")
    @Size(max = 200, message = "La direccion es de máximo 200 caracteres")
    private String direccion;
}
