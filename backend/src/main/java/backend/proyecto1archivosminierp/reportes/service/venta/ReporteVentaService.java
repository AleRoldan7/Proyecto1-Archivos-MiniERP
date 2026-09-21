package backend.proyecto1archivosminierp.reportes.service.venta;

import backend.proyecto1archivosminierp.enums.PeriodoReporte;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.reportes.dto.venta.ClienteTopDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ProductoIngresadoDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.VentaReporteDTO;
import backend.proyecto1archivosminierp.reportes.repository.venta.ReporteVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static backend.proyecto1archivosminierp.util.RangoFechas.desdeSeguro;
import static backend.proyecto1archivosminierp.util.RangoFechas.hastaSeguro;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteVentaService {

    private final ReporteVentaRepository reporteVentaRepository;

    public List<VentaReporteDTO> ventasPorRango(LocalDateTime desde, LocalDateTime hasta) {
        validarRangoObligatorio(desde, hasta);
        return reporteVentaRepository.ventasPorRango(desde, hasta);
    }

    public List<ClienteTopDTO> topClientes(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRangoOpcional(desde, hasta);
        return reporteVentaRepository.topClientesPorMonto(
                desdeSeguro(desde), hastaSeguro(hasta), pagina(limite, 10));
    }

    public List<ProductoIngresadoDTO> topProductosPorIngreso(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRangoOpcional(desde, hasta);
        return reporteVentaRepository.topProductosPorIngreso(
                desdeSeguro(desde), hastaSeguro(hasta), pagina(limite, 10));
    }
    
    public List<ResumenVentaDTO> resumenPorPeriodo(PeriodoReporte periodo,
                                                   LocalDateTime desde, LocalDateTime hasta) {
        validarRangoObligatorio(desde, hasta);

        if (periodo == null) {
            throw new DatosInvalidos("Debe indicar el período de agrupamiento");
        }

        List<ResumenVentaDTO> resumen = switch (periodo) {
            case DIARIO -> reporteVentaRepository.resumenDiario(desde, hasta);
            case SEMANAL -> reporteVentaRepository.resumenSemanal(desde, hasta);
            case MENSUAL -> reporteVentaRepository.resumenMensual(desde, hasta);
            case ANUAL -> reporteVentaRepository.resumenAnual(desde, hasta);
        };

        resumen.forEach(fila -> fila.setPeriodo(armarEtiqueta(periodo, fila)));

        return resumen;
    }

    private String armarEtiqueta(PeriodoReporte periodo, ResumenVentaDTO fila) {
        return switch (periodo) {
            case DIARIO -> String.format("%02d/%02d/%d", fila.getDia(), fila.getSegmento(), fila.getYear());
            case SEMANAL -> String.format("Semana %02d - %d", fila.getSegmento(), fila.getYear());
            case MENSUAL -> String.format("%02d/%d", fila.getSegmento(), fila.getYear());
            case ANUAL -> String.valueOf(fila.getYear());
        };
    }

    private Pageable pagina(Integer limite, int porDefecto) {
        int tamanio = (limite == null || limite <= 0) ? porDefecto : limite;
        return PageRequest.of(0, tamanio);
    }

    private void validarRangoObligatorio(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new DatosInvalidos("Debe indicar la fecha inicial y la fecha final");
        }
        validarRangoOpcional(desde, hasta);
    }

    private void validarRangoOpcional(LocalDateTime desde, LocalDateTime hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new DatosInvalidos("La fecha inicial no puede ser mayor que la fecha final");
        }
    }
}
