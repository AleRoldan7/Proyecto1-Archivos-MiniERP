package backend.proyecto1archivosminierp.inventario.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoteInventarioResponseDTO {

    private Integer idLote;
    private Integer idCompra;
    private Integer idProducto;
    private Integer cantidadLote;
    private BigDecimal precioUnitario;
    private LocalDateTime fechaEntrada;
}
