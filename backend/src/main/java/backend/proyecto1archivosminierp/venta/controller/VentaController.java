package backend.proyecto1archivosminierp.venta.controller;

import backend.proyecto1archivosminierp.venta.dto.VentaRequestDTO;
import backend.proyecto1archivosminierp.venta.dto.VentaResponseDTO;
import backend.proyecto1archivosminierp.venta.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VENTAS', 'ADMIN')")
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/registrar-venta")
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody VentaRequestDTO ventaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.create(ventaRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> getVentaId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.getById(id));
    }

    @GetMapping("/lista-ventas")
    public ResponseEntity<List<VentaResponseDTO>> getAllVenta() {
        return ResponseEntity.ok(ventaService.getAll());
    }

    @GetMapping("/{id}/factura")
    public ResponseEntity<byte[]> descargarFactura(@PathVariable Integer id) {
        byte[] pdf = ventaService.generarFactura(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"factura-" + id + ".pdf\"")
                .body(pdf);
    }
}
