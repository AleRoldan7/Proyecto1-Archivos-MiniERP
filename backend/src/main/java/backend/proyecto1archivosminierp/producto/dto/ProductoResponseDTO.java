package backend.proyecto1archivosminierp.producto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoResponseDTO {

    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Integer idCategoria;
    private String nombreCategoria;
    private Boolean estadoProducto;
}
