package backend.proyecto1archivosminierp.reportes.service.compra;

import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.reportes.dto.compra.CompraReporteDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProductoCompradoDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProveedorTopDTO;
import backend.proyecto1archivosminierp.reportes.repository.compra.ReporteCompraRepository;
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
public class ReporteCompraService {

    private final ReporteCompraRepository reporteCompraRepository;

    public List<CompraReporteDTO> comprasPorRango(LocalDateTime desde, LocalDateTime hasta) {
        validarRangoObligatorio(desde, hasta);
        return reporteCompraRepository.comprasPorRango(desde, hasta);
    }

    public List<ProveedorTopDTO> topProveedores(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRangoOpcional(desde, hasta);
        return reporteCompraRepository.topProveedoresPorMonto(
                desdeSeguro(desde), hastaSeguro(hasta), pagina(limite, 5));
    }

    public List<ProductoCompradoDTO> productosMasAdquiridos(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRangoOpcional(desde, hasta);
        return reporteCompraRepository.productosMasAdquiridos(
                desdeSeguro(desde), hastaSeguro(hasta), pagina(limite, 10));
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
