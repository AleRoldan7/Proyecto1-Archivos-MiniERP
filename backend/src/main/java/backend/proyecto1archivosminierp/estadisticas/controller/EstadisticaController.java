package backend.proyecto1archivosminierp.estadisticas.controller;

import backend.proyecto1archivosminierp.cliente.repository.ClienteRepository;
import backend.proyecto1archivosminierp.compra.repository.CompraRepository;
import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import backend.proyecto1archivosminierp.inventario.repository.InventarioRepository;
import backend.proyecto1archivosminierp.producto.repository.ProductoRepository;
import backend.proyecto1archivosminierp.proveedor.repository.ProveedorRepository;
import backend.proyecto1archivosminierp.usuario.repository.UsuarioRepository;
import backend.proyecto1archivosminierp.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompraRepository compraRepository;
    private final VentaRepository ventaRepository;
    private final InventarioRepository inventarioRepository;


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> adminStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalProductos = productoRepository.count();
        long totalProveedores = proveedorRepository.count();
        long totalClientes = clienteRepository.count();
        long totalUsuarios = usuarioRepository.count();

        List<InventarioEntity> inventarios = inventarioRepository.findAll();
        long stockBajo = inventarios.stream()
                .filter(i -> i.getStock() <= i.getStockMinimo())
                .count();

        stats.put("totalProductos", totalProductos);
        stats.put("totalProveedores", totalProveedores);
        stats.put("totalClientes", totalClientes);
        stats.put("totalUsuarios", totalUsuarios);
        stats.put("stockBajo", stockBajo);

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/compras")
    @PreAuthorize("hasAnyRole('ADMIN','COMPRAS')")
    public ResponseEntity<Map<String, Object>> comprasStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalCompras = compraRepository.count();
        long totalProveedores = proveedorRepository.count();

        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        BigDecimal montoMes = compraRepository.sumTotalDesde(inicioMes);

        stats.put("totalCompras", totalCompras);
        stats.put("totalProveedores", totalProveedores);
        stats.put("montoMes", montoMes != null ? montoMes : BigDecimal.ZERO);

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/inventario")
    @PreAuthorize("hasAnyRole('ADMIN','INVENTARIO')")
    public ResponseEntity<Map<String, Object>> inventarioStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalProductos = productoRepository.count();
        List<InventarioEntity> inventarios = inventarioRepository.findAll();

        long totalExistencias = inventarios.stream()
                .mapToLong(InventarioEntity::getStock)
                .sum();

        long stockBajo = inventarios.stream()
                .filter(i -> i.getStock() > 0 && i.getStock() <= i.getStockMinimo())
                .count();

        long agotados = inventarios.stream()
                .filter(i -> i.getStock() == 0)
                .count();

        stats.put("totalProductos", totalProductos);
        stats.put("totalExistencias", totalExistencias);
        stats.put("stockBajo", stockBajo);
        stats.put("agotados", agotados);

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/ventas")
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    public ResponseEntity<Map<String, Object>> ventasStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalVentas = ventaRepository.count();
        long totalClientes = clienteRepository.count();

        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        BigDecimal montoMes = ventaRepository.sumTotalDesde(inicioMes);

        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        BigDecimal montoHoy = ventaRepository.sumTotalDesde(inicioDia);

        stats.put("totalVentas", totalVentas);
        stats.put("totalClientes", totalClientes);
        stats.put("montoMes", montoMes != null ? montoMes : BigDecimal.ZERO);
        stats.put("montoHoy", montoHoy != null ? montoHoy : BigDecimal.ZERO);

        return ResponseEntity.ok(stats);
    }
}
