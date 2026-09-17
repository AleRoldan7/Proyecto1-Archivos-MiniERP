package backend.proyecto1archivosminierp.producto.controller;

import backend.proyecto1archivosminierp.producto.dto.CategoriaRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.CategoriaResponseDTO;
import backend.proyecto1archivosminierp.producto.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear-categoria")
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(categoriaRequestDTO));
    }

    @PutMapping("/actualizar-categoria/{idCategoria}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Integer idCategoria,
                                                       @Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.update(idCategoria, categoriaRequestDTO));
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(categoriaService.getById(idCategoria));
    }

    @GetMapping("/lista-categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> getAll() {
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @DeleteMapping("/eliminar-categoria/{idCategoria}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCategoria) {
        categoriaService.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }
}
