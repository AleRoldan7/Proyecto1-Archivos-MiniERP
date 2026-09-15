package backend.proyecto1archivosminierp.compra.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleResponseDTO {

    private Integer idDetalle;
    private Integer idCompra;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
