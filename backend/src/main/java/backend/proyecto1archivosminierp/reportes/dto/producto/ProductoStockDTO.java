package backend.proyecto1archivosminierp.reportes.dto.producto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoStockDTO {

    private Integer idInventario;
    private Integer idProducto;
    private String nombreProducto;
    private Integer stock;
    private Integer stockMinimo;
    private Boolean bajoMinimo;

    public ProductoStockDTO(Integer idInventario, Integer idProducto, String nombreProducto, Integer stock, Integer stockMinimo) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }
}
