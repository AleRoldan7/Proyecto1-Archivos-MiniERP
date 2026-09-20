package backend.proyecto1archivosminierp.producto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
}
