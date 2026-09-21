package backend.proyecto1archivosminierp.compra.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompraRequestDTO {

    @NotNull(message = "El proveedor es obligatorio")
    private Integer idProveedor;

    @NotEmpty(message = "La compra debe contener al menos un producto")
    @Valid
    private List<DetalleRequestDTO> detalles;
}
