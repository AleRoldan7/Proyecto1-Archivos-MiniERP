package backend.proyecto1archivosminierp.producto.dto;

import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreProducto;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcionProducto;

    @NotNull(message = "Debe seleccionar una categoría")
    private Integer idCategoria;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "1.00", message = "El precio de venta debe ser mayor a 0")
    private BigDecimal precioVenta;

    @NotEmpty(message = "Debe asociar al menos un proveedor")
    @Valid
    private List<ProductoProveedorRequestDTO> proveedores;
}
