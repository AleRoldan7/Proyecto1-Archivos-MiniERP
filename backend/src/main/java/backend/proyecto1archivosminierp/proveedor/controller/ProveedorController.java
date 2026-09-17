package backend.proyecto1archivosminierp.proveedor.controller;

import backend.proyecto1archivosminierp.proveedor.dto.ProveedorRequestDTO;
import backend.proyecto1archivosminierp.proveedor.dto.ProveedorResponseDTO;
import backend.proyecto1archivosminierp.proveedor.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPRAS','ADMIN')")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping("/crear-proveedor")
    public ResponseEntity<ProveedorResponseDTO> crearProveedor(@Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.create(proveedorRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(proveedorService.getById(id));
    }

    @GetMapping("/lista-proveedores")
    public ResponseEntity<List<ProveedorResponseDTO>> getAllProveedores() {
        return ResponseEntity.status(HttpStatus.OK).body(proveedorService.getAll());
    }

    @PutMapping("/actualizar-proveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(@PathVariable Integer id, @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        return ResponseEntity.ok(proveedorService.update(id, proveedorRequestDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
