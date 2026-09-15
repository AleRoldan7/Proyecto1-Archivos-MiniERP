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
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import backend.proyecto1archivosminierp.inventario.entity.LoteInventarioEntity;
import backend.proyecto1archivosminierp.inventario.repository.InventarioRepository;
import backend.proyecto1archivosminierp.inventario.repository.LoteInventarioRepository;
import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import backend.proyecto1archivosminierp.producto.repository.ProductoRepository;
import backend.proyecto1archivosminierp.proveedor.entity.ProveedorEntity;
import backend.proyecto1archivosminierp.proveedor.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    @Transactional
    public CompraResponseDTO create(CompraRequestDTO compraRequestDTO) {

        ProveedorEntity proveedorEntity = proveedorRepository.findById(compraRequestDTO.getIdProveedor()).
                orElseThrow(() -> new RuntimeException("Proveedor no encontrado con nombre " + compraRequestDTO.getIdProveedor()));

        CompraEntity compraEntity = new CompraEntity();
        compraEntity.setProveedor(proveedorEntity);
        compraEntity.setFechaCompra(LocalDateTime.now());
        CompraEntity savedCompraEntity = compraRepository.save(compraEntity);

        for (DetalleRequestDTO detalleDTO : compraRequestDTO.getDetalles()) {

            ProductoEntity producto = productoRepository.findById(Long.valueOf(detalleDTO.getIdProducto()))
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getIdProducto()));

            DetalleCompraEntity detalleCompraEntity = new DetalleCompraEntity();

            detalleCompraEntity.setCompra(savedCompraEntity);
            detalleCompraEntity.setProducto(producto);
            detalleCompraEntity.setCantidad(detalleDTO.getCantidad());
            detalleCompraEntity.setPrecioUnitario(detalleDTO.getPrecioUnitario());

            detalleCompraRepository.save(detalleCompraEntity);

            LoteInventarioEntity loteInventarioEntity = new LoteInventarioEntity();

            loteInventarioEntity.setCompra(savedCompraEntity);
            loteInventarioEntity.setProducto(producto);
            loteInventarioEntity.setCantidadLote(detalleDTO.getCantidad());
            loteInventarioEntity.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            loteInventarioEntity.setFechaEntrada(LocalDateTime.now());

            loteInventarioRepository.save(loteInventarioEntity);

            InventarioEntity inventario = inventarioRepository.findByProductoIdProducto(producto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto: " + producto.getIdProducto()));

            inventario.setStock(inventario.getStock() + detalleDTO.getCantidad());

            inventarioRepository.save(inventario);
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

        CompraEntity compraEntity = compraRepository.findById(idCompra).orElseThrow(() -> new RuntimeException("Compra no encontrada" +
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
