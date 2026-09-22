package backend.proyecto1archivosminierp.reportes.controller;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import backend.proyecto1archivosminierp.enums.PeriodoReporte;
import backend.proyecto1archivosminierp.reportes.dto.compra.CompraReporteDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProductoCompradoDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProveedorTopDTO;
import backend.proyecto1archivosminierp.reportes.dto.log.LogResponseDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.MovimientoHistorialDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoMovimientoDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoStockDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoVendidoDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ClienteTopDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ProductoIngresadoDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.VentaReporteDTO;
import backend.proyecto1archivosminierp.reportes.service.compra.ReporteCompraService;
import backend.proyecto1archivosminierp.reportes.service.inventario.ReporteInventarioService;
import backend.proyecto1archivosminierp.reportes.service.log.LogService;
import backend.proyecto1archivosminierp.reportes.service.venta.ReporteVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteInventarioService reporteInventarioService;
    private final ReporteCompraService reporteCompraService;
    private final ReporteVentaService reporteVentaService;
    private final LogService logService;

    @GetMapping("/prueba")
    public ResponseEntity<String> prueba() {
        return ResponseEntity.ok("ADMIN autorizado");
    }
    // ---------- Productos e inventario ----------


    @GetMapping("/mas-vendidos")
    @PreAuthorize("hasAnyRole('INVENTARIO','ADMIN')")
    public ResponseEntity<List<ProductoVendidoDTO>> topProductosMasVendidos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(reporteInventarioService.topProductosMasVendidos(desde, hasta, limite));
    }

    @GetMapping("/productos/menor-existencia")
    @PreAuthorize("hasAnyRole('INVENTARIO','ADMIN')")
    public ResponseEntity<List<ProductoStockDTO>> productosMenorExistencia(
            @RequestParam(defaultValue = "false") boolean soloBajoMinimo,
            @RequestParam(defaultValue = "20") Integer limite) {
        return ResponseEntity.ok(reporteInventarioService.productosMenorExistencia(soloBajoMinimo, limite));
    }

    @GetMapping("/productos/mas-movimientos")
    @PreAuthorize("hasAnyRole('INVENTARIO','ADMIN')")
    public ResponseEntity<List<ProductoMovimientoDTO>> productosConMasMovimientos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(reporteInventarioService.productosConMasMovimientos(desde, hasta, limite));
    }

    @GetMapping("/productos/{idProducto}/movimientos")
    @PreAuthorize("hasAnyRole('INVENTARIO','ADMIN')")
    public ResponseEntity<List<MovimientoHistorialDTO>> historialDeProducto(
            @PathVariable Long idProducto,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reporteInventarioService.historialDeProducto(idProducto, desde, hasta));
    }

    // ---------- Compras y proveedores ----------

    @GetMapping("/compras")
    @PreAuthorize("hasAnyRole('COMPRAS','ADMIN')")
    public ResponseEntity<List<CompraReporteDTO>> comprasPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reporteCompraService.comprasPorRango(desde, hasta));
    }

    @GetMapping("/proveedores/top")
    @PreAuthorize("hasAnyRole('COMPRAS','ADMIN')")
    public ResponseEntity<List<ProveedorTopDTO>> topProveedores(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "5") Integer limite) {
        return ResponseEntity.ok(reporteCompraService.topProveedores(desde, hasta, limite));
    }

    @GetMapping("/productos/mas-adquiridos")
    @PreAuthorize("hasAnyRole('COMPRAS','ADMIN')")
    public ResponseEntity<List<ProductoCompradoDTO>> productosMasAdquiridos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(reporteCompraService.productosMasAdquiridos(desde, hasta, limite));
    }

    // ---------- Ventas y clientes ----------

    @GetMapping("/ventas")
    @PreAuthorize("hasAnyRole('VENTAS','ADMIN')")
    public ResponseEntity<List<VentaReporteDTO>> ventasPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reporteVentaService.ventasPorRango(desde, hasta));
    }

    @GetMapping("/clientes/top")
    @PreAuthorize("hasAnyRole('VENTAS','ADMIN')")
    public ResponseEntity<List<ClienteTopDTO>> topClientes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(reporteVentaService.topClientes(desde, hasta, limite));
    }

    @GetMapping("/productos/mayores-ingresos")
    @PreAuthorize("hasAnyRole('VENTAS','ADMIN')")
    public ResponseEntity<List<ProductoIngresadoDTO>> topProductosPorIngreso(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(reporteVentaService.topProductosPorIngreso(desde, hasta, limite));
    }

    @GetMapping("/ventas/resumen")
    @PreAuthorize("hasAnyRole('VENTAS','ADMIN')")
    public ResponseEntity<List<ResumenVentaDTO>> resumenVentas(
            @RequestParam(defaultValue = "MENSUAL") PeriodoReporte periodo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reporteVentaService.resumenPorPeriodo(periodo, desde, hasta));
    }

    // ---------- Logs ----------

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LogResponseDTO>> logs(
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String modulo,
            @RequestParam(required = false) AccionesLog accion,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "200") Integer limite) {
        return ResponseEntity.ok(logService.consultar(usuario, modulo, accion, desde, hasta, limite));
    }
}
