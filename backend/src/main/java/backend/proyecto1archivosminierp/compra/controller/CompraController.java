package backend.proyecto1archivosminierp.compra.controller;

import backend.proyecto1archivosminierp.compra.dto.CompraRequestDTO;
import backend.proyecto1archivosminierp.compra.dto.CompraResponseDTO;
import backend.proyecto1archivosminierp.compra.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
@RequiredArgsConstructor
@PreAuthorize("hasRole('COMPRAS')")
public class CompraController {

    private final CompraService compraService;

    @PostMapping("/registrar-compra")
    public ResponseEntity<CompraResponseDTO> create(@Valid @RequestBody CompraRequestDTO compraRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.create(compraRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.getById(id));
    }

    @GetMapping("/lista-compras")
    public ResponseEntity<List<CompraResponseDTO>> getAll() {
        return ResponseEntity.ok(compraService.getAll());
    }

    @PutMapping("/actualizar-compra/{id}")
    public ResponseEntity<CompraResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody CompraRequestDTO compraRequestDTO) {
        return ResponseEntity.ok(compraService.update(id, compraRequestDTO));
    }

    @DeleteMapping("/eliminar-compra/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
