package backend.proyecto1archivosminierp.reportes.dto.log;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogResponseDTO {

    private Integer idLog;
    private String usuario;
    private String modulo;
    private AccionesLog accion;
    private String operacion;
    private String descripcion;
    private Boolean exitoso;
    private LocalDateTime fechaLog;
}
