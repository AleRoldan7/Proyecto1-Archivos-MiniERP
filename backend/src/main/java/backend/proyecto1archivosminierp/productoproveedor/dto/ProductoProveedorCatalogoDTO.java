package backend.proyecto1archivosminierp.productoproveedor.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoProveedorCatalogoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal precioCompra;
}