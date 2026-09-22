package backend.proyecto1archivosminierp.venta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaRequestDTO {

    private Integer idProducto;
    private Integer cantidad;
    //private BigDecimal precioUnitario;

}
