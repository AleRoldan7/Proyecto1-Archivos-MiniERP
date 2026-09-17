package backend.proyecto1archivosminierp.reportes.dto.venta;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResumenVentaDTO {

    private String periodo;
    private Integer year;
    private Integer segmento;
    private Integer dia;
    private Long numeroVentas;
    private BigDecimal totalVendido;

    public ResumenVentaDTO(Integer year, Integer segmento, Integer dia, Long numeroVentas, BigDecimal totalVendido) {
        this.year = year;
        this.segmento = segmento;
        this.dia = dia;
        this.numeroVentas = numeroVentas;
        this.totalVendido = totalVendido;
    }

    public ResumenVentaDTO(Integer year, Integer segmento, Long numeroVentas, BigDecimal totalVendido) {
        this.year = year;
        this.segmento = segmento;
        this.numeroVentas = numeroVentas;
        this.totalVendido = totalVendido;
    }

    public ResumenVentaDTO(Integer year, Long numeroVentas, BigDecimal totalVendido) {
        this.year = year;
        this.numeroVentas = numeroVentas;
        this.totalVendido = totalVendido;
    }
}
