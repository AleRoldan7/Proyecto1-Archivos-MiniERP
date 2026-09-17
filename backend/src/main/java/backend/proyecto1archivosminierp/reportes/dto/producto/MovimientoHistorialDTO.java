package backend.proyecto1archivosminierp.reportes.dto.producto;

import backend.proyecto1archivosminierp.enums.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MovimientoHistorialDTO {

    private Integer idMovimiento;
    private Integer idProducto;
    private String nombreProducto;
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime fechaMovimiento;
    private String motivo;
    private Integer idLote;
    private Integer idVenta;
}
