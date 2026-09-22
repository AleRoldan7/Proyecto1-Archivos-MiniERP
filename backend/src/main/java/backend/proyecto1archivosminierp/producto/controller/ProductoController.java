package backend.proyecto1archivosminierp.producto.controller;

import backend.proyecto1archivosminierp.producto.dto.ProductoRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.ProductoResponseDTO;
import backend.proyecto1archivosminierp.producto.service.ProductoService;
import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorCatalogoDTO;
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
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/crear-producto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(productoRequestDTO));
    }

    @PutMapping("/actualizar-producto/{idProducto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Integer idProducto, @Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.ok(productoService.update(idProducto, productoRequestDTO));
    }

    @GetMapping("/{idProducto}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPRAS', 'VENTAS', 'INVENTARIO')")
    public ResponseEntity<ProductoResponseDTO> getById(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(productoService.getById(idProducto));
    }

    @GetMapping("/lista-productos")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPRAS', 'VENTAS', 'INVENTARIO')")
    public ResponseEntity<List<ProductoResponseDTO>> getAll() {
        return ResponseEntity.ok(productoService.getAll());
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPRAS', 'VENTAS', 'INVENTARIO')")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(productoService.buscar(texto, idCategoria, estado));
    }

    @DeleteMapping("/desactivar-producto/{idProducto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer idProducto) {
        productoService.delete(idProducto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idProducto}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> activar(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(productoService.activar(idProducto));
    }

    @GetMapping("/proveedor/{idProveedor}/catalogo")
    @PreAuthorize("hasAnyRole('COMPRAS','ADMIN')")
    public ResponseEntity<List<ProductoProveedorCatalogoDTO>> catalogoPorProveedor(
            @PathVariable Integer idProveedor) {
        return ResponseEntity.ok(productoService.catalogoPorProveedor(idProveedor));
    }

    @GetMapping("/disponibles-para-venta")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPRAS', 'VENTAS', 'INVENTARIO')")
    public ResponseEntity<List<ProductoResponseDTO>> disponiblesParaVenta() {
        return ResponseEntity.ok(productoService.disponiblesParaVenta());
    }
}
