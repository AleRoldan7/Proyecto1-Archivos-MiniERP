package backend.proyecto1archivosminierp.inventario.controller;

import backend.proyecto1archivosminierp.inventario.dto.InventarioRequestDTO;
import backend.proyecto1archivosminierp.inventario.dto.InventarioResponseDTO;
import backend.proyecto1archivosminierp.inventario.dto.LoteInventarioResponseDTO;
import backend.proyecto1archivosminierp.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('INVENTARIO','ADMIN')")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping("/lista-inventario")
    public ResponseEntity<List<InventarioResponseDTO>> getAll() {
        return ResponseEntity.ok(inventarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.getById(id));
    }

    @PutMapping("/actualizar-inventario/{id}")
    public ResponseEntity<InventarioResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody InventarioRequestDTO inventarioRequestDTO) {
        return ResponseEntity.ok(inventarioService.update(id, inventarioRequestDTO));
    }

    @GetMapping("/producto/{idProducto}/lotes")
    public ResponseEntity<List<LoteInventarioResponseDTO>> getLotesPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(inventarioService.getLotesPorProducto(idProducto));
    }
}
