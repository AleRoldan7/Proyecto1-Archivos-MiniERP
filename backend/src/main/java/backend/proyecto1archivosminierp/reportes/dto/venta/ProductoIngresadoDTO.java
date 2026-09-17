package backend.proyecto1archivosminierp.reportes.dto.venta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoIngresadoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Long unidadesVendidas;
    private BigDecimal ingresoTotal;
}
