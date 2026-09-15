package backend.proyecto1archivosminierp.inventario.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioRequestDTO {

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

}
