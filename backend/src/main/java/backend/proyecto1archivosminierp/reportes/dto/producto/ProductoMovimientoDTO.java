package backend.proyecto1archivosminierp.reportes.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoMovimientoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Long totalMovimientos;
    private Long unidadesMovidas;
}
