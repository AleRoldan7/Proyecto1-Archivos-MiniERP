package backend.proyecto1archivosminierp.proveedor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorResponseDTO {

    private Integer idProveedor;
    private String nombreProveedor;
    private String telefonoProveedor;
    private String correoProveedor;
    private String direccionProveedor;
    private Boolean estadoProveedor;
}
