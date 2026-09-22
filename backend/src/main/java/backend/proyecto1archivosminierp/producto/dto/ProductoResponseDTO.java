package backend.proyecto1archivosminierp.producto.dto;

import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductoResponseDTO {

    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Integer idCategoria;
    private String nombreCategoria;
    private Boolean estadoProducto;
    private BigDecimal precioVenta;
    private List<ProductoProveedorResponseDTO> proveedores;

}
