package backend.proyecto1archivosminierp.reportes.dto.compra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoCompradoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Long vecesComprado;        // en cuántas compras distintas aparece
    private Long unidadesCompradas;
    private BigDecimal montoTotal;
}
