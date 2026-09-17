package backend.proyecto1archivosminierp.producto.controller;

import backend.proyecto1archivosminierp.producto.dto.ProductoRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.ProductoResponseDTO;
import backend.proyecto1archivosminierp.producto.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/crear-producto")
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(productoRequestDTO));
    }

    @PutMapping("/actualizar-producto/{idProducto}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Integer idProducto, @Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.ok(productoService.update(idProducto, productoRequestDTO));
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDTO> getById(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(productoService.getById(idProducto));
    }

    /**
     * Solo productos activos.
     */
    @GetMapping("/lista-productos")
    public ResponseEntity<List<ProductoResponseDTO>> getAll() {
        return ResponseEntity.ok(productoService.getAll());
    }

    /**
     * Búsqueda con filtros: ?texto=jabon&idCategoria=2&estado=true
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(productoService.buscar(texto, idCategoria, estado));
    }

    /**
     * Baja lógica.
     */
    @DeleteMapping("/desactivar-producto/{idProducto}")
    public ResponseEntity<Void> delete(@PathVariable Integer idProducto) {
        productoService.delete(idProducto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idProducto}/activar")
    public ResponseEntity<ProductoResponseDTO> activar(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(productoService.activar(idProducto));
    }
}
