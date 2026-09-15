package backend.proyecto1archivosminierp.inventario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioResponseDTO {

    private Integer idInventario;
    private Integer idProducto;
    private String nombreProducto;
    private Integer stock;
    private Integer stockMinimo;
}
