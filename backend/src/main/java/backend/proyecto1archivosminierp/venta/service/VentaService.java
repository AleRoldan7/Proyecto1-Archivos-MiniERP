package backend.proyecto1archivosminierp.venta.service;

import backend.proyecto1archivosminierp.cliente.entity.ClienteEntity;
import backend.proyecto1archivosminierp.cliente.repository.ClienteRepository;
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
import backend.proyecto1archivosminierp.venta.dto.DetalleVentaRequestDTO;
import backend.proyecto1archivosminierp.venta.dto.DetalleVentaResponseDTO;
import backend.proyecto1archivosminierp.venta.dto.VentaRequestDTO;
import backend.proyecto1archivosminierp.venta.dto.VentaResponseDTO;
import backend.proyecto1archivosminierp.venta.entity.DetalleVentaEntity;
import backend.proyecto1archivosminierp.venta.entity.VentaEntity;
import backend.proyecto1archivosminierp.venta.repository.DetalleVentaRepository;
import backend.proyecto1archivosminierp.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService implements Crud<VentaRequestDTO, VentaResponseDTO, Integer> {


    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteInventarioRepository loteInventarioRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private static final BigDecimal IVA = new BigDecimal("0.12");


    @Override
    @Transactional
    public VentaResponseDTO create(VentaRequestDTO ventaRequestDTO) {
        validarVenta(ventaRequestDTO);
        ClienteEntity clienteEntity = buscarCliente(ventaRequestDTO.getIdCliente());
        BigDecimal subTotal = calcularSubtotal(ventaRequestDTO.getDetalles());
        VentaEntity ventaEntity = crearVentaEntity(clienteEntity, subTotal);
        VentaEntity savedVenta = ventaRepository.save(ventaEntity);
        List<DetalleVentaEntity> detalles = procesarDetalles(ventaRequestDTO.getDetalles(), savedVenta);
        return convertirVenta(savedVenta, detalles);

    }

    @Override
    public VentaResponseDTO update(Integer integer, VentaRequestDTO ventaRequestDTO) {
        throw new OperacionInvalida("No se puede modificar una venta que ya se registro");
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO getById(Integer idVenta) {
        VentaEntity venta = ventaRepository.findById(idVenta).orElseThrow(() ->
                new DatosNoEncontrados("No existe la venta con el id " + idVenta));

        return convertirVentaCompleta(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> getAll() {
        return ventaRepository.findAll().stream().map(this::convertirVentaCompleta).toList();
    }

    @Override
    public void delete(Integer integer) {
        throw new OperacionInvalida("No se puede eliminar una venta que ya se registro");
    }

    private void validarVenta(VentaRequestDTO ventaRequestDTO) {

        if (ventaRequestDTO == null) {
            throw new DatosInvalidos("Los datos para una venta son obligatorios");
        }

        if (ventaRequestDTO.getIdCliente() == null) {
            throw new DatosInvalidos("Debe de seleccionar un cliente");
        }

        if (ventaRequestDTO.getDetalles() == null || ventaRequestDTO.getDetalles().isEmpty()) {
            throw new DatosInvalidos("La venta debe tener al menos un producto");
        }

        for (DetalleVentaRequestDTO detalle : ventaRequestDTO.getDetalles()) {
            validarDetalle(detalle);
        }
    }

    private void validarDetalle(DetalleVentaRequestDTO detalleVentaRequestDTO) {

        if (detalleVentaRequestDTO == null) {
            throw new DatosInvalidos("El detalle de venta no puede ser nulo");
        }

        if (detalleVentaRequestDTO.getIdProducto() == null) {
            throw new DatosInvalidos("Debe seleccionar un producto");
        }

        if (detalleVentaRequestDTO.getCantidad() == null || detalleVentaRequestDTO.getCantidad() <= 0) {
            throw new DatosInvalidos("La cantidad debe ser mayor que cero");
        }

        if (detalleVentaRequestDTO.getPrecioUnitario() == null || detalleVentaRequestDTO.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DatosInvalidos("El precio unitario debe ser mayor que cero");
        }

    }

    private ClienteEntity buscarCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(() -> new DatosInvalidos("El cliente no existe con id " + idCliente));
    }

    private ProductoEntity buscarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto).orElseThrow(()
                -> new DatosNoEncontrados("No existe el producto con el id " + idProducto));
    }

    private BigDecimal calcularSubtotal(List<DetalleVentaRequestDTO> detalles) {

        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVentaRequestDTO detalle : detalles) {
            BigDecimal subtotalDetalle = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            subtotal = subtotal.add(subtotalDetalle);
        }

        return subtotal;
    }

    private VentaEntity crearVentaEntity(ClienteEntity clienteEntity, BigDecimal subtotal) {

        BigDecimal iva = subtotal.multiply(IVA);

        BigDecimal total = subtotal.add(iva);

        VentaEntity ventaEntity = new VentaEntity();

        ventaEntity.setCliente(clienteEntity);
        ventaEntity.setFechaVenta(LocalDateTime.now());
        ventaEntity.setSubtotal(subtotal);
        ventaEntity.setIvaVenta(iva);
        ventaEntity.setTotal(total);

        return ventaEntity;
    }

    private List<DetalleVentaEntity> procesarDetalles(List<DetalleVentaRequestDTO> detallesRequest, VentaEntity ventaEntity) {

        List<DetalleVentaEntity> detalles = new ArrayList<>();

        for (DetalleVentaRequestDTO detalleVentaRequestDTO : detallesRequest) {

            ProductoEntity producto = buscarProducto(detalleVentaRequestDTO.getIdProducto());

            descontarInventarioPEPS(producto, detalleVentaRequestDTO.getCantidad(), ventaEntity);

            DetalleVentaEntity detalle = crearDetalle(detalleVentaRequestDTO, producto, ventaEntity);

            detalles.add(detalleVentaRepository.save(detalle));
        }

        return detalles;
    }


    private DetalleVentaEntity crearDetalle(DetalleVentaRequestDTO detalleVentaRequestDTO,
                                            ProductoEntity productoEntity, VentaEntity ventaEntity) {

        DetalleVentaEntity detalle = new DetalleVentaEntity();
        detalle.setVenta(ventaEntity);
        detalle.setProducto(productoEntity);
        detalle.setCantidad(detalleVentaRequestDTO.getCantidad());
        detalle.setPrecioUnitario(detalleVentaRequestDTO.getPrecioUnitario());
        return detalle;
    }

    private void descontarInventarioPEPS(ProductoEntity productoEntity, Integer cantidadVenta, VentaEntity ventaEntity) {

        InventarioEntity inventarioEntity = buscarInventario(productoEntity);

        validarStock(inventarioEntity, cantidadVenta, productoEntity);

        List<LoteInventarioEntity> lotes = obtenerLotes(productoEntity);

        int cantidadPendiente = cantidadVenta;

        for (LoteInventarioEntity lote : lotes) {

            if (cantidadPendiente <= 0) {
                break;
            }

            if (lote.getCantidadLote() <= 0) {
                continue;
            }

            int cantidadDescontar = calcularCantidadDescontar(lote, cantidadPendiente);

            actualizarLote(lote, cantidadDescontar);

            registrarMovimiento(productoEntity, ventaEntity, lote, cantidadDescontar);

            cantidadPendiente = cantidadPendiente - cantidadDescontar;
        }

        validarCantidadPendiente(cantidadPendiente, productoEntity);

        actualizarStock(inventarioEntity, cantidadVenta);
    }

    private InventarioEntity buscarInventario(ProductoEntity productoEntity) {
        return inventarioRepository.findByProductoIdProducto(productoEntity.getIdProducto()).orElseThrow(() ->
                new DatosNoEncontrados("No existe inventario para el producto " + productoEntity.getNombreProducto()));
    }


    private void validarStock(InventarioEntity inventarioEntity, Integer cantidad, ProductoEntity productoEntity) {

        if (inventarioEntity.getStock() < cantidad) {
            throw new DatosInvalidos("Stock insuficiente para el producto " + productoEntity.getNombreProducto() + ". Disponible: "
                    + inventarioEntity.getStock() + ", solicitado: " + cantidad);
        }
    }


    private List<LoteInventarioEntity> obtenerLotes(ProductoEntity productoEntity) {
        return loteInventarioRepository.findByProductoIdProductoOrderByFechaEntradaAscIdLoteAsc(productoEntity.getIdProducto());
    }


    private int calcularCantidadDescontar(LoteInventarioEntity loteInventarioEntity, int cantidadPendiente) {
        return Math.min(loteInventarioEntity.getCantidadLote(), cantidadPendiente);
    }

    private void actualizarLote(LoteInventarioEntity loteInventarioEntity, int cantidadDescontar) {
        loteInventarioEntity.setCantidadLote(loteInventarioEntity.getCantidadLote() - cantidadDescontar);
        loteInventarioRepository.save(loteInventarioEntity);
    }


    private void registrarMovimiento(ProductoEntity productoEntity, VentaEntity ventaEntity,
                                     LoteInventarioEntity loteInventarioEntity, int cantidad) {

        MovimientoInventarioEntity movimientoInventarioEntity = new MovimientoInventarioEntity();

        movimientoInventarioEntity.setProducto(productoEntity);
        movimientoInventarioEntity.setVenta(ventaEntity);
        movimientoInventarioEntity.setLote(loteInventarioEntity);
        movimientoInventarioEntity.setCantidad(cantidad);

        movimientoInventarioEntity.setTipoMovimiento(TipoMovimiento.SALIDA);
        movimientoInventarioEntity.setFechaMovimiento(LocalDateTime.now());
        movimientoInventarioEntity.setMotivo("VENTA");

        movimientoInventarioRepository.save(movimientoInventarioEntity);
    }


    private void validarCantidadPendiente(int cantidadPendiente, ProductoEntity productoEntity) {
        if (cantidadPendiente > 0) {
            throw new DatosInvalidos("Los lotes no tienen suficiente " + "cantidad para el producto " + productoEntity.getNombreProducto());
        }
    }


    private void actualizarStock(InventarioEntity inventarioEntity, int cantidadVenta) {
        inventarioEntity.setStock(inventarioEntity.getStock() - cantidadVenta);
        inventarioRepository.save(inventarioEntity);
    }


    private VentaResponseDTO convertirVentaCompleta(VentaEntity ventaEntity) {

        List<DetalleVentaEntity> detalles = detalleVentaRepository.findByVenta(ventaEntity);
        return convertirVenta(ventaEntity, detalles);
    }

    private VentaResponseDTO convertirVenta(VentaEntity ventaEntity, List<DetalleVentaEntity> detalles) {

        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO();

        ventaResponseDTO.setIdVenta(ventaEntity.getIdVenta());

        ventaResponseDTO.setIdCliente(ventaEntity.getCliente().getIdCliente());

        ventaResponseDTO.setNombreCliente(ventaEntity.getCliente().getNombre());

        ventaResponseDTO.setFechaVenta(ventaEntity.getFechaVenta());

        ventaResponseDTO.setSubtotal(ventaEntity.getSubtotal());

        ventaResponseDTO.setIvaVenta(ventaEntity.getIvaVenta());

        ventaResponseDTO.setTotal(ventaEntity.getTotal());

        ventaResponseDTO.setDetalles(detalles.stream().map(this::convertirDetalle).toList());

        return ventaResponseDTO;
    }

    private DetalleVentaResponseDTO convertirDetalle(DetalleVentaEntity detalleVentaEntity) {

        DetalleVentaResponseDTO detalleVentaResponseDTO = new DetalleVentaResponseDTO();

        detalleVentaResponseDTO.setIdDetalleVenta(detalleVentaEntity.getIdDetalleVenta());

        detalleVentaResponseDTO.setIdProducto(detalleVentaEntity.getProducto().getIdProducto());

        detalleVentaResponseDTO.setDescripcionProducto(detalleVentaEntity.getProducto().getNombreProducto());

        detalleVentaResponseDTO.setCantidad(detalleVentaEntity.getCantidad());

        detalleVentaResponseDTO.setPrecioUnitario(detalleVentaEntity.getPrecioUnitario());

        detalleVentaResponseDTO.setSubtotal(detalleVentaEntity.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleVentaEntity.getCantidad())));

        return detalleVentaResponseDTO;
    }
}
