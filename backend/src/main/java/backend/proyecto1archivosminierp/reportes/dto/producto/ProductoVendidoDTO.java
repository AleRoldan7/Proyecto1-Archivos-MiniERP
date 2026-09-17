package backend.proyecto1archivosminierp.reportes.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoVendidoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Long cantidadVendida;
    private BigDecimal montoTotal;
}
