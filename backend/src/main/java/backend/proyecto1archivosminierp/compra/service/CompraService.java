package backend.proyecto1archivosminierp.compra.service;

import backend.proyecto1archivosminierp.compra.dto.CompraRequestDTO;
import backend.proyecto1archivosminierp.compra.dto.CompraResponseDTO;
import backend.proyecto1archivosminierp.compra.dto.DetalleRequestDTO;
import backend.proyecto1archivosminierp.compra.dto.DetalleResponseDTO;
import backend.proyecto1archivosminierp.compra.entity.CompraEntity;
import backend.proyecto1archivosminierp.compra.entity.DetalleCompraEntity;
import backend.proyecto1archivosminierp.compra.repository.CompraRepository;
import backend.proyecto1archivosminierp.compra.repository.DetalleCompraRepository;
import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.enums.TipoMovimiento;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import backend.proyecto1archivosminierp.inventario.entity.LoteInventarioEntity;
import backend.proyecto1archivosminierp.inventario.entity.MovimientoInventarioEntity;
import backend.proyecto1archivosminierp.inventario.repository.InventarioRepository;
import backend.proyecto1archivosminierp.inventario.repository.LoteInventarioRepository;
import backend.proyecto1archivosminierp.inventario.repository.MovimientoInventarioRepository;
import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import backend.proyecto1archivosminierp.producto.repository.ProductoRepository;
import backend.proyecto1archivosminierp.productoproveedor.entity.ProductoProveedorEntity;
import backend.proyecto1archivosminierp.productoproveedor.repository.ProductoProveedoRepository;
import backend.proyecto1archivosminierp.proveedor.entity.ProveedorEntity;
import backend.proyecto1archivosminierp.proveedor.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService implements Crud<CompraRequestDTO, CompraResponseDTO, Integer> {

    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteInventarioRepository loteInventarioRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoProveedoRepository productoProveedoRepository;

    @Override
    @Transactional
    public CompraResponseDTO create(CompraRequestDTO compraRequestDTO) {

        ProveedorEntity proveedorEntity = proveedorRepository.findById(compraRequestDTO.getIdProveedor()).
                orElseThrow(() -> new DatosNoEncontrados("Proveedor no encontrado con nombre " + compraRequestDTO.getIdProveedor()));

        CompraEntity compraEntity = new CompraEntity();
        compraEntity.setProveedor(proveedorEntity);
        compraEntity.setFechaCompra(LocalDateTime.now());
        CompraEntity savedCompraEntity = compraRepository.save(compraEntity);

        for (DetalleRequestDTO detalleDTO : compraRequestDTO.getDetalles()) {

            ProductoEntity producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new DatosNoEncontrados("Producto no encontrado con ID: " + detalleDTO.getIdProducto()));

            ProductoProveedorEntity productoProveedorEntity = productoProveedoRepository.
                    findByProductoIdProductoAndProveedorIdProveedor(detalleDTO.getIdProducto(),
                            proveedorEntity.getIdProveedor()).orElseThrow(() -> new DatosInvalidos("El proveedor '"
                            + proveedorEntity.getNombreProveedor() + "' no vende el producto '" + producto.getNombreProducto() + "'"));


            if (!productoProveedorEntity.isEstado()) {
                throw new DatosInvalidos("La asociación producto-proveedor está inactiva");
            }

            BigDecimal precioCompra = productoProveedorEntity.getPrecioCompra();

            DetalleCompraEntity detalleCompraEntity = new DetalleCompraEntity();

            detalleCompraEntity.setCompra(savedCompraEntity);
            detalleCompraEntity.setProducto(producto);
            detalleCompraEntity.setCantidad(detalleDTO.getCantidad());
            detalleCompraEntity.setPrecioUnitario(precioCompra);

            detalleCompraRepository.save(detalleCompraEntity);

            LoteInventarioEntity loteInventarioEntity = new LoteInventarioEntity();

            loteInventarioEntity.setCompra(savedCompraEntity);
            loteInventarioEntity.setProducto(producto);
            loteInventarioEntity.setCantidadLote(detalleDTO.getCantidad());
            loteInventarioEntity.setPrecioUnitario(precioCompra);
            loteInventarioEntity.setFechaEntrada(LocalDateTime.now());

            LoteInventarioEntity savedLote = loteInventarioRepository.save(loteInventarioEntity);

            /*
            InventarioEntity inventario = inventarioRepository.findByProductoIdProducto(producto.getIdProducto())
                    .orElseThrow(() -> new DatosNoEncontrados("Inventario no encontrado para el producto: " + producto.getIdProducto()));
            */


            InventarioEntity inventario = inventarioRepository
                    .findByProductoIdProducto(producto.getIdProducto())
                    .orElseGet(() -> {
                        InventarioEntity nuevo = new InventarioEntity();
                        nuevo.setProducto(producto);
                        nuevo.setStock(0);
                        nuevo.setStockMinimo(1);
                        return inventarioRepository.save(nuevo);
                    });
            inventario.setStock(inventario.getStock() + detalleDTO.getCantidad());

            inventarioRepository.save(inventario);

            MovimientoInventarioEntity movimiento = new MovimientoInventarioEntity();
            movimiento.setCompra(savedCompraEntity);
            movimiento.setProducto(producto);
            movimiento.setLote(savedLote);
            movimiento.setCantidad(detalleDTO.getCantidad());
            movimiento.setTipoMovimiento(TipoMovimiento.ENTRADA);
            movimiento.setFechaMovimiento(LocalDateTime.now());
            movimiento.setMotivo("COMPRA");
            movimientoInventarioRepository.save(movimiento);
        }

        return devuelveCompra(savedCompraEntity);
    }

    @Override
    public CompraResponseDTO update(Integer integer, CompraRequestDTO compraRequestDTO) {
        throw new OperacionInvalida("No se puede modificar una compra que ya se registro");
    }

    @Override
    @Transactional(readOnly = true)
    public CompraResponseDTO getById(Integer idCompra) {

        CompraEntity compraEntity = compraRepository.findById(idCompra).orElseThrow(() -> new DatosNoEncontrados("Compra no encontrada" +
                " con id: " + idCompra));

        return devuelveCompra(compraEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraResponseDTO> getAll() {
        return compraRepository.findAll().stream().map(this::devuelveCompra).toList();
    }

    @Override
    public void delete(Integer integer) {
        throw new OperacionInvalida("No se puede eliminar una compra que ya se realizo");
    }

    private CompraResponseDTO devuelveCompra(CompraEntity compraEntity) {

        CompraResponseDTO compraResponseDTO = new CompraResponseDTO();

        compraResponseDTO.setIdCompra(compraEntity.getIdCompra());
        compraResponseDTO.setIdProveedor(compraEntity.getProveedor().getIdProveedor());
        compraResponseDTO.setFechaCompra(compraEntity.getFechaCompra());
        List<DetalleCompraEntity> detalles = detalleCompraRepository.findByCompraIdCompra(compraEntity.getIdCompra());
        List<DetalleResponseDTO> detalleResponseDTOS = new ArrayList<>();

        for (DetalleCompraEntity detalleCompraEntity : detalles) {
            DetalleResponseDTO detalleResponseDTO = new DetalleResponseDTO();
            detalleResponseDTO.setIdDetalle(detalleCompraEntity.getIdDetalle());
            detalleResponseDTO.setIdCompra(detalleCompraEntity.getCompra().getIdCompra());
            detalleResponseDTO.setIdProducto(detalleCompraEntity.getProducto().getIdProducto());
            detalleResponseDTO.setCantidad(detalleCompraEntity.getCantidad());
            detalleResponseDTO.setPrecioUnitario(detalleCompraEntity.getPrecioUnitario());
            detalleResponseDTOS.add(detalleResponseDTO);
        }

        compraResponseDTO.setDetalles(detalleResponseDTOS);

        return compraResponseDTO;
    }
}
