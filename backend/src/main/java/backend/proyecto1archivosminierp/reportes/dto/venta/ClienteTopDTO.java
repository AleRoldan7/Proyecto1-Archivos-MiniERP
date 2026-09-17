package backend.proyecto1archivosminierp.reportes.dto.venta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ClienteTopDTO {

    private Integer idCliente;
    private String nombreCliente;
    private Long numeroVentas;
    private BigDecimal montoAcumulado;

}
