package backend.proyecto1archivosminierp.producto.service;

import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import backend.proyecto1archivosminierp.inventario.repository.InventarioRepository;
import backend.proyecto1archivosminierp.producto.dto.ProductoRequestDTO;
import backend.proyecto1archivosminierp.producto.dto.ProductoResponseDTO;
import backend.proyecto1archivosminierp.producto.entity.CategoriaEntity;
import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import backend.proyecto1archivosminierp.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements Crud<ProductoRequestDTO, ProductoResponseDTO, Integer> {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
    private final InventarioRepository inventarioRepository;

    @Override
    @Transactional
    public ProductoResponseDTO create(ProductoRequestDTO productoRequestDTO) {

        validar(productoRequestDTO);

        if (productoRepository.existsByNombreProductoIgnoreCase(productoRequestDTO.getNombreProducto().trim())) {
            throw new DatosInvalidos("Ya existe un producto con el nombre " + productoRequestDTO.getNombreProducto());
        }

        CategoriaEntity categoriaEntity = categoriaService.buscarCategoria(productoRequestDTO.getIdCategoria());

        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombreProducto(productoRequestDTO.getNombreProducto().trim());
        productoEntity.setDescripcionProducto(productoRequestDTO.getDescripcionProducto());
        productoEntity.setCategoria(categoriaEntity);
        productoEntity.setEstadoProducto(true);

        ProductoEntity savedProducto = productoRepository.save(productoEntity);

        crearInventarioInicial(savedProducto, productoRequestDTO.getStockMinimo());

        return devuelveProducto(savedProducto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO update(Integer idProducto, ProductoRequestDTO productoRequestDTO) {

        validar(productoRequestDTO);

        ProductoEntity productoEntity = buscarProducto(idProducto);

        if (productoRepository.existsByNombreProductoIgnoreCaseAndIdProductoNot(
                productoRequestDTO.getNombreProducto().trim(), idProducto)) {
            throw new DatosInvalidos("Ya existe otro producto con el nombre " + productoRequestDTO.getNombreProducto());
        }

        CategoriaEntity categoriaEntity = categoriaService.buscarCategoria(productoRequestDTO.getIdCategoria());

        productoEntity.setNombreProducto(productoRequestDTO.getNombreProducto().trim());
        productoEntity.setDescripcionProducto(productoRequestDTO.getDescripcionProducto());
        productoEntity.setCategoria(categoriaEntity);

        return devuelveProducto(productoRepository.save(productoEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO getById(Integer idProducto) {
        return devuelveProducto(buscarProducto(idProducto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getAll() {
        return productoRepository.findByEstadoProductoTrueOrderByNombreProductoAsc()
                .stream().map(this::devuelveProducto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscar(String texto, Integer idCategoria, Boolean estado) {
        String filtro = (texto == null || texto.isBlank()) ? null : texto.trim();
        return productoRepository.buscar(filtro, idCategoria, estado)
                .stream().map(this::devuelveProducto).toList();
    }

    /**
     * Baja lógica. No se borra físicamente porque el producto está referenciado por
     * detalle_compra, detalle_venta, lote_inventario y movimiento_inventario, y eso
     * destruiría el historial de los reportes.
     */
    @Override
    @Transactional
    public void delete(Integer idProducto) {

        ProductoEntity productoEntity = buscarProducto(idProducto);

        if (!productoEntity.isEstadoProducto()) {
            throw new OperacionInvalida("El producto ya se encuentra inactivo");
        }

        Integer stock = inventarioRepository.findByProductoIdProducto(idProducto)
                .map(InventarioEntity::getStock)
                .orElse(0);

        if (stock > 0) {
            throw new OperacionInvalida("No se puede desactivar el producto porque aún tiene "
                    + stock + " unidades en existencia");
        }

        productoEntity.setEstadoProducto(false);
        productoRepository.save(productoEntity);
    }

    @Transactional
    public ProductoResponseDTO activar(Integer idProducto) {

        ProductoEntity productoEntity = buscarProducto(idProducto);

        if (productoEntity.isEstadoProducto()) {
            throw new OperacionInvalida("El producto ya se encuentra activo");
        }

        productoEntity.setEstadoProducto(true);

        return devuelveProducto(productoRepository.save(productoEntity));
    }

    private void crearInventarioInicial(ProductoEntity productoEntity, Integer stockMinimo) {

        if (inventarioRepository.findByProductoIdProducto(productoEntity.getIdProducto()).isPresent()) {
            return;
        }

        InventarioEntity inventarioEntity = new InventarioEntity();
        inventarioEntity.setProducto(productoEntity);
        inventarioEntity.setStock(0);
        inventarioEntity.setStockMinimo(stockMinimo == null ? 0 : stockMinimo);

        inventarioRepository.save(inventarioEntity);
    }

    private ProductoEntity buscarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new DatosNoEncontrados("No existe el producto con id " + idProducto));
    }

    private void validar(ProductoRequestDTO productoRequestDTO) {

        if (productoRequestDTO == null) {
            throw new DatosInvalidos("Los datos del producto son obligatorios");
        }

        if (productoRequestDTO.getNombreProducto() == null || productoRequestDTO.getNombreProducto().isBlank()) {
            throw new DatosInvalidos("El nombre del producto es obligatorio");
        }

        if (productoRequestDTO.getIdCategoria() == null) {
            throw new DatosInvalidos("Debe seleccionar una categoría");
        }

        if (productoRequestDTO.getStockMinimo() != null && productoRequestDTO.getStockMinimo() < 0) {
            throw new DatosInvalidos("El stock mínimo no puede ser negativo");
        }
    }

    private ProductoResponseDTO devuelveProducto(ProductoEntity productoEntity) {
        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();
        productoResponseDTO.setIdProducto(productoEntity.getIdProducto());
        productoResponseDTO.setNombreProducto(productoEntity.getNombreProducto());
        productoResponseDTO.setDescripcionProducto(productoEntity.getDescripcionProducto());
        productoResponseDTO.setIdCategoria(productoEntity.getCategoria().getIdCategoria());
        productoResponseDTO.setNombreCategoria(productoEntity.getCategoria().getNombreCategoria());
        productoResponseDTO.setEstadoProducto(productoEntity.isEstadoProducto());
        return productoResponseDTO;
    }
}