package backend.proyecto1archivosminierp.productoproveedor.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoProveedorResponseDTO {

    private Integer idProductoProveedor;
    private Integer idProveedor;
    private String nombreProveedor;
    private BigDecimal precioCompra;
}