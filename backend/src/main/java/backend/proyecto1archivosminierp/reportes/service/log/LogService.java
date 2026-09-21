package backend.proyecto1archivosminierp.reportes.service.log;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import backend.proyecto1archivosminierp.reportes.dto.log.LogResponseDTO;
import backend.proyecto1archivosminierp.reportes.entity.LogEntity;
import backend.proyecto1archivosminierp.reportes.repository.log.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static backend.proyecto1archivosminierp.util.RangoFechas.desdeSeguro;
import static backend.proyecto1archivosminierp.util.RangoFechas.hastaSeguro;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    /**
     * REQUIRES_NEW para que el log se guarde aunque la transacción de negocio
     * termine haciendo rollback (es justamente el caso de accion = ERROR).
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(String usuario, String modulo, AccionesLog accion,
                          String operacion, String descripcion, boolean exitoso) {

        LogEntity log = new LogEntity();
        log.setUsuario(usuario == null ? "SISTEMA" : usuario);
        log.setModulo(modulo);
        log.setAccionesLog(accion);
        log.setOperacion(operacion);
        log.setDescripcion(recortar(descripcion));
        log.setExitoso(exitoso);
        log.setFechaLog(LocalDateTime.now());

        logRepository.save(log);
    }

    public List<LogResponseDTO> consultar(String usuario, String modulo, AccionesLog accion,
                                          LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        int tamanio = (limite == null || limite <= 0) ? 200 : limite;
        return logRepository.buscar(
                usuario, modulo, accion,
                desdeSeguro(desde), hastaSeguro(hasta),
                PageRequest.of(0, tamanio)
        ).stream().map(this::convertir).toList();
    }

    private String recortar(String texto) {
        if (texto == null) {
            return null;
        }
        return texto.length() > 500 ? texto.substring(0, 500) : texto;
    }

    private LogResponseDTO convertir(LogEntity logEntity) {
        LogResponseDTO logResponseDTO = new LogResponseDTO();
        logResponseDTO.setIdLog(logEntity.getIdLog());
        logResponseDTO.setUsuario(logEntity.getUsuario());
        logResponseDTO.setModulo(logEntity.getModulo());
        logResponseDTO.setAccion(logEntity.getAccionesLog());
        logResponseDTO.setOperacion(logEntity.getOperacion());
        logResponseDTO.setDescripcion(logEntity.getDescripcion());
        logResponseDTO.setExitoso(logEntity.getExitoso());
        logResponseDTO.setFechaLog(logEntity.getFechaLog());
        return logResponseDTO;
    }

}
