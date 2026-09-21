package backend.proyecto1archivosminierp.reportes.service.inventario;

import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.reportes.dto.producto.MovimientoHistorialDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoMovimientoDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoStockDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoVendidoDTO;
import backend.proyecto1archivosminierp.reportes.repository.producto.ReporteProductoRepository;
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
public class ReporteInventarioService {

    private final ReporteProductoRepository reporteProductoRepository;

    public List<ProductoVendidoDTO> topProductosMasVendidos(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRango(desde, hasta);
        return reporteProductoRepository.topProductosMasVendidos(
                desdeSeguro(desde),
                hastaSeguro(hasta),
                pagina(limite, 10));
    }

    public List<ProductoStockDTO> productosMenorExistencia(boolean soloBajoMinimo, Integer limite) {
        List<ProductoStockDTO> productos =
                reporteProductoRepository.productosConMenorExistencia(soloBajoMinimo, pagina(limite, 20));
        productos.forEach(p -> p.setBajoMinimo(p.getStock() <= p.getStockMinimo()));
        return productos;
    }

    public List<ProductoMovimientoDTO> productosConMasMovimientos(LocalDateTime desde, LocalDateTime hasta, Integer limite) {
        validarRango(desde, hasta);
        return reporteProductoRepository.productosConMasMovimientos(
                desdeSeguro(desde),
                hastaSeguro(hasta),
                pagina(limite, 10));
    }

    public List<MovimientoHistorialDTO> historialDeProducto(Long idProducto, LocalDateTime desde, LocalDateTime hasta) {
        if (idProducto == null) {
            throw new DatosInvalidos("Debe seleccionar un producto");
        }
        validarRango(desde, hasta);
        return reporteProductoRepository.historialDeProducto(
                idProducto,
                desdeSeguro(desde),
                hastaSeguro(hasta));
    }

    private Pageable pagina(Integer limite, int porDefecto) {
        int tamanio = (limite == null || limite <= 0) ? porDefecto : limite;
        return PageRequest.of(0, tamanio);
    }

    private void validarRango(LocalDateTime desde, LocalDateTime hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new DatosInvalidos("La fecha inicial no puede ser mayor que la fecha final");
        }
    }
}