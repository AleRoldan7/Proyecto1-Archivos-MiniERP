package backend.proyecto1archivosminierp.venta.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VentaResponseDTO {

    private Integer idVenta;
    private Integer idCliente;
    private String nombreCliente;
    private LocalDateTime fechaVenta;
    private BigDecimal subtotal;
    private BigDecimal ivaVenta;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles;
}
