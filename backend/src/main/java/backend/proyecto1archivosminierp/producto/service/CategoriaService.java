package backend.proyecto1archivosminierp.producto.service;

import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.producto.dto.CategoriaRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.CategoriaResponseDTO;
import backend.proyecto1archivosminierp.producto.entity.CategoriaEntity;
import backend.proyecto1archivosminierp.producto.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO categoriaRequestDTO) throws DatosInvalidos {

        if (categoriaRepository.existsByNombreCategoria(categoriaRequestDTO.getNombreCategoria())) {
            new DatosInvalidos("La categoria ya existe en el sistema");
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setNombreCategoria(categoriaRequestDTO.getNombreCategoria());
        categoriaEntity.setDescripcion(categoriaRequestDTO.getDescripcion());

        CategoriaEntity guardar = categoriaRepository.save(categoriaEntity);

        CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO();

        categoriaResponseDTO.setIdCategoria(guardar.getIdCategoria());
        categoriaResponseDTO.setNombreCategoria(guardar.getNombreCategoria());
        categoriaResponseDTO.setDescripcion(guardar.getDescripcion());

        return categoriaResponseDTO;

    }

    @Transactional
    public List<CategoriaResponseDTO> listarCategorias() {

        return categoriaRepository.findAll().stream().map(categoria -> {
            CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO();

            categoriaResponseDTO.setIdCategoria(categoria.getIdCategoria());
            categoriaResponseDTO.setNombreCategoria(categoria.getNombreCategoria());
            categoriaResponseDTO.setDescripcion(categoria.getDescripcion());
            return categoriaResponseDTO;

        }).toList();
    }

}
