package backend.proyecto1archivosminierp.producto.service;

import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.producto.dto.CategoriaRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.CategoriaResponseDTO;
import backend.proyecto1archivosminierp.producto.entity.CategoriaEntity;
import backend.proyecto1archivosminierp.producto.repository.CategoriaRepository;
import backend.proyecto1archivosminierp.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements Crud<CategoriaRequestDTO, CategoriaResponseDTO, Integer> {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {

        validar(categoriaRequestDTO);

        if (categoriaRepository.existsByNombreCategoriaIgnoreCase(categoriaRequestDTO.getNombreCategoria().trim())) {
            throw new DatosInvalidos("Ya existe una categoría con el nombre " + categoriaRequestDTO.getNombreCategoria());
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombreCategoria(categoriaRequestDTO.getNombreCategoria().trim());
        categoriaEntity.setDescripcion(categoriaRequestDTO.getDescripcion());

        return devuelveCategoria(categoriaRepository.save(categoriaEntity));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Integer idCategoria, CategoriaRequestDTO categoriaRequestDTO) {

        validar(categoriaRequestDTO);

        CategoriaEntity categoriaEntity = buscarCategoria(idCategoria);

        if (categoriaRepository.existsByNombreCategoriaIgnoreCaseAndIdCategoriaNot(
                categoriaRequestDTO.getNombreCategoria().trim(), idCategoria)) {
            throw new DatosInvalidos("Ya existe otra categoría con el nombre " + categoriaRequestDTO.getNombreCategoria());
        }

        categoriaEntity.setNombreCategoria(categoriaRequestDTO.getNombreCategoria().trim());
        categoriaEntity.setDescripcion(categoriaRequestDTO.getDescripcion());

        return devuelveCategoria(categoriaRepository.save(categoriaEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO getById(Integer idCategoria) {
        return devuelveCategoria(buscarCategoria(idCategoria));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> getAll() {
        return categoriaRepository.findAll().stream().map(this::devuelveCategoria).toList();
    }

    @Override
    @Transactional
    public void delete(Integer idCategoria) {

        CategoriaEntity categoriaEntity = buscarCategoria(idCategoria);

        long productos = productoRepository.countByCategoriaIdCategoria(idCategoria);

        if (productos > 0) {
            throw new OperacionInvalida("No se puede eliminar la categoría porque tiene "
                    + productos + " producto(s) asociado(s)");
        }

        categoriaRepository.delete(categoriaEntity);
    }

    CategoriaEntity buscarCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new DatosNoEncontrados("No existe la categoría con id " + idCategoria));
    }

    private void validar(CategoriaRequestDTO categoriaRequestDTO) {

        if (categoriaRequestDTO == null) {
            throw new DatosInvalidos("Los datos de la categoría son obligatorios");
        }

        if (categoriaRequestDTO.getNombreCategoria() == null || categoriaRequestDTO.getNombreCategoria().isBlank()) {
            throw new DatosInvalidos("El nombre de la categoría es obligatorio");
        }
    }

    private CategoriaResponseDTO devuelveCategoria(CategoriaEntity categoriaEntity) {
        CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO();
        categoriaResponseDTO.setIdCategoria(categoriaEntity.getIdCategoria());
        categoriaResponseDTO.setNombreCategoria(categoriaEntity.getNombreCategoria());
        categoriaResponseDTO.setDescripcion(categoriaEntity.getDescripcion());
        categoriaResponseDTO.setTotalProductos(productoRepository.countByCategoriaIdCategoria(categoriaEntity.getIdCategoria()));
        return categoriaResponseDTO;
    }
}
