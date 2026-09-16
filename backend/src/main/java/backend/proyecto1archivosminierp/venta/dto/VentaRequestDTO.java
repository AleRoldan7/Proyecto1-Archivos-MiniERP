package backend.proyecto1archivosminierp.venta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VentaRequestDTO {

    private Integer idCliente;
    private List<DetalleVentaRequestDTO> detalles;

}
