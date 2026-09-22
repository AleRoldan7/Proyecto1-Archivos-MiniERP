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
import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorCatalogoDTO;
import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorRequestDTO;
import backend.proyecto1archivosminierp.productoproveedor.dto.ProductoProveedorResponseDTO;
import backend.proyecto1archivosminierp.productoproveedor.entity.ProductoProveedorEntity;
import backend.proyecto1archivosminierp.productoproveedor.repository.ProductoProveedoRepository;
import backend.proyecto1archivosminierp.proveedor.entity.ProveedorEntity;
import backend.proyecto1archivosminierp.proveedor.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements Crud<ProductoRequestDTO, ProductoResponseDTO, Integer> {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
    private final InventarioRepository inventarioRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoProveedoRepository productoProveedorRepository;

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
        productoEntity.setPrecioVenta(productoRequestDTO.getPrecioVenta());

        ProductoEntity savedProducto = productoRepository.save(productoEntity);

        crearInventarioInicial(savedProducto, productoRequestDTO.getStockMinimo());
        guardarProveedores(savedProducto, productoRequestDTO.getProveedores());
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
        productoEntity.setPrecioVenta(productoRequestDTO.getPrecioVenta());

        ProductoEntity updateProducto = productoRepository.save(productoEntity);

        if (productoRequestDTO.getStockMinimo() != null) {
            inventarioRepository.findByProductoIdProducto(idProducto).ifPresent(inv -> {
                inv.setStockMinimo(productoRequestDTO.getStockMinimo());
                inventarioRepository.save(inv);
            });
        }

        if (productoRequestDTO.getProveedores() != null) {
            productoProveedorRepository.deleteByProductoIdProducto(idProducto);
            guardarProveedores(updateProducto, productoRequestDTO.getProveedores());
        }
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

    @Transactional(readOnly = true)
    public List<ProductoProveedorCatalogoDTO> catalogoPorProveedor(Integer idProveedor) {

        return productoProveedorRepository.buscarCatalogoPorProveedor(idProveedor)
                .stream()
                .map(pp -> {
                    ProductoProveedorCatalogoDTO dto = new ProductoProveedorCatalogoDTO();
                    dto.setIdProducto(pp.getProducto().getIdProducto());
                    dto.setNombreProducto(pp.getProducto().getNombreProducto());
                    dto.setPrecioCompra(pp.getPrecioCompra());
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> disponiblesParaVenta() {
        return productoRepository.findDisponiblesParaVenta()
                .stream()
                .map(this::devuelveProducto)
                .toList();
    }
    
    private void guardarProveedores(ProductoEntity producto, List<ProductoProveedorRequestDTO> proveedores) {

        for (ProductoProveedorRequestDTO ppDTO : proveedores) {

            ProveedorEntity proveedor = proveedorRepository.findById(ppDTO.getIdProveedor())
                    .orElseThrow(() -> new DatosNoEncontrados(
                            "No existe el proveedor con id " + ppDTO.getIdProveedor()));

            if (Boolean.FALSE.equals(proveedor.getEstadoProveedor())) {
                throw new DatosInvalidos("El proveedor '" + proveedor.getNombreProveedor() + "' está inactivo");
            }

            ProductoProveedorEntity productoProveedorEntity = new ProductoProveedorEntity();
            productoProveedorEntity.setProducto(producto);
            productoProveedorEntity.setProveedor(proveedor);
            productoProveedorEntity.setPrecioCompra(ppDTO.getPrecioCompra());
            productoProveedorEntity.setEstado(true);
            productoProveedorEntity.setFechaCreacion(LocalDateTime.now());

            productoProveedorRepository.save(productoProveedorEntity);
        }
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

        BigDecimal precio = productoRequestDTO.getPrecioVenta();
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DatosInvalidos("El precio de venta debe ser mayor a 0");
        }

        if (productoRequestDTO.getProveedores() == null || productoRequestDTO.getProveedores().isEmpty()) {
            throw new DatosInvalidos("Debe de tener algún proveedor el producto");
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
        productoResponseDTO.setPrecioVenta(productoEntity.getPrecioVenta());

        List<ProductoProveedorEntity> proveedores = productoProveedorRepository.findByProductoIdProductoAndEstadoTrue(productoEntity.getIdProducto());
        productoResponseDTO.setProveedores(proveedores.stream().map(this::devuelveProductoProveedor).toList());
        return productoResponseDTO;
    }

    private ProductoProveedorResponseDTO devuelveProductoProveedor(ProductoProveedorEntity productoProveedorEntity) {
        ProductoProveedorResponseDTO productoProveedorResponseDTO = new ProductoProveedorResponseDTO();
        productoProveedorResponseDTO.setIdProductoProveedor(productoProveedorEntity.getIdProductoProveedor());
        productoProveedorResponseDTO.setIdProveedor(productoProveedorEntity.getProveedor().getIdProveedor());
        productoProveedorResponseDTO.setNombreProveedor(productoProveedorEntity.getProveedor().getNombreProveedor());
        productoProveedorResponseDTO.setPrecioCompra(productoProveedorEntity.getPrecioCompra());
        return productoProveedorResponseDTO;
    }
}