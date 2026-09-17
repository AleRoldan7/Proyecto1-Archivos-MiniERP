package backend.proyecto1archivosminierp.reportes.dto.venta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class VentaReporteDTO {

    private Integer idVenta;
    private Integer idCliente;
    private String nombreCliente;
    private LocalDateTime fechaVenta;
    private BigDecimal subtotal;
    private BigDecimal ivaVenta;
    private BigDecimal total;
}
