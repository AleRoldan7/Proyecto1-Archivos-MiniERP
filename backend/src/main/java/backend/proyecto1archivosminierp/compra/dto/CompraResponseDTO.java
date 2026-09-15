package backend.proyecto1archivosminierp.compra.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompraResponseDTO {

    private Integer idCompra;
    private Integer idProveedor;
    private LocalDateTime fechaCompra;
    private List<DetalleResponseDTO> detalles;
}
