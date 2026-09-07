package backend.proyecto1archivosminierp.producto.controller;

import backend.proyecto1archivosminierp.producto.dto.CategoriaRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.CategoriaResponseDTO;
import backend.proyecto1archivosminierp.producto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear-categoria")
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@RequestBody CategoriaRequestDTO categoriaRequestDTO) {

        CategoriaResponseDTO categoriaResponseDTO = categoriaService.crearCategoria(categoriaRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponseDTO);
    }

    @GetMapping("/lista-categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {

        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listarCategorias());
    }
}
