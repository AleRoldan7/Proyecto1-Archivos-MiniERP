package backend.proyecto1archivosminierp.venta.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleVentaRequestDTO {

    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;

}
