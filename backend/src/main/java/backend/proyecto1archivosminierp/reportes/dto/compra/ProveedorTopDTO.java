package backend.proyecto1archivosminierp.reportes.dto.compra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProveedorTopDTO {

    private Integer idProveedor;
    private String nombreProveedor;
    private Long numeroCompras;
    private BigDecimal montoAcumulado;
}
