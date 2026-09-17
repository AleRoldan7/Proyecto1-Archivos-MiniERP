package backend.proyecto1archivosminierp.reportes.dto.compra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CompraReporteDTO {

    private Integer idCompra;
    private Integer idProveedor;
    private String nombreProveedor;
    private LocalDateTime fechaCompra;
    private Long unidades;
    private BigDecimal total;
}
