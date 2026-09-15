package backend.proyecto1archivosminierp.proveedor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre de proveedor es obligatorio")
    @Size(max = 150, message = "El nombre debe de ser de maximo 150 caracteres")
    private String nombreProveedor;

    @NotBlank(message = "El teléfono de proveedor es obligatorio")
    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    private String telefonoProveedor;

    @NotBlank(message = "El correo de proveedor es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 50, message = "El correo no puede superar los 50 caracteres")
    private String correoProveedor;

    @NotBlank(message = "La dirección de proveedor es obligatoria")
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String direccionProveedor;

    private Boolean estadoProveedor;

}
