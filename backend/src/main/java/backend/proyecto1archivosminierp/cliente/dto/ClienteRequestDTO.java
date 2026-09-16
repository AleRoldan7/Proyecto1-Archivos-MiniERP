package backend.proyecto1archivosminierp.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 250, message = "El nombre es de máximo 250 caracteres")
    private String nombre;

    @NotBlank(message = "El nit del cliente es obligatorio")
    @Size(max = 10, message = "El nit es de máximo 10 caracteres")
    private String nit;

    @NotBlank(message = "El dpi del cliente es obligatorio")
    @Size(max = 13, message = " El dpi es de máximo 13 caracteres")
    private String dpi;

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
