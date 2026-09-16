package backend.proyecto1archivosminierp.cliente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    private Integer idCliente;
    private String nombre;
    private String nit;
    private String dpi;
    private String telefono;
    private String correo;
    private String direccion;
}
