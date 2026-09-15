package backend.proyecto1archivosminierp.inventario.service;

import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.inventario.dto.InventarioRequestDTO;
import backend.proyecto1archivosminierp.inventario.dto.InventarioResponseDTO;
import backend.proyecto1archivosminierp.inventario.dto.LoteInventarioResponseDTO;
import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import backend.proyecto1archivosminierp.inventario.entity.LoteInventarioEntity;
import backend.proyecto1archivosminierp.inventario.repository.InventarioRepository;
import backend.proyecto1archivosminierp.inventario.repository.LoteInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService implements Crud<InventarioRequestDTO, InventarioResponseDTO, Integer> {

    private final InventarioRepository inventarioRepository;
    private final LoteInventarioRepository loteInventarioRepository;

    @Override
    @Transactional
    public InventarioResponseDTO create(InventarioRequestDTO inventarioRequestDTO) {
        throw new DatosNoEncontrados("El invnetario se genera cuando se comprar productos");
    }

    @Override
    public InventarioResponseDTO update(Integer idInventario, InventarioRequestDTO inventarioRequestDTO) {

        InventarioEntity inventarioEntity = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new DatosNoEncontrados("El invnetario no existe con id" + idInventario));

        inventarioEntity.setStockMinimo(inventarioRequestDTO.getStockMinimo());

        return devuelveInventario(inventarioRepository.save(inventarioEntity));

    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponseDTO getById(Integer idInventario) {

        InventarioEntity inventarioEntity = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new DatosNoEncontrados("El invnetario no existe con id" + idInventario));

        return devuelveInventario(inventarioEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> getAll() {
        return inventarioRepository.findAll().stream().map(this::devuelveInventario).toList();
    }

    @Override
    public void delete(Integer idInventario) {
        throw new OperacionInvalida("No se puede eliminar un inventario en el sistema");

    }

    @Transactional(readOnly = true)
    public List<LoteInventarioResponseDTO> getLotesPorProducto(Integer idProducto) {
        return loteInventarioRepository.findByProductoIdProductoOrderByFechaEntradaAscIdLoteAsc(idProducto)
                .stream().map(this::devuelveLote).toList();
    }

    private InventarioResponseDTO devuelveInventario(InventarioEntity inventarioEntity) {
        InventarioResponseDTO inventarioResponseDTO = new InventarioResponseDTO();
        inventarioResponseDTO.setIdInventario(inventarioEntity.getIdInventario());
        inventarioResponseDTO.setIdProducto(inventarioEntity.getProducto().getIdProducto());
        inventarioResponseDTO.setNombreProducto(inventarioEntity.getProducto().getNombreProducto());
        inventarioResponseDTO.setStock(inventarioEntity.getStock());
        inventarioResponseDTO.setStockMinimo(inventarioEntity.getStockMinimo());
        return inventarioResponseDTO;
    }

    private LoteInventarioResponseDTO devuelveLote(LoteInventarioEntity loteInventarioEntity) {
        LoteInventarioResponseDTO loteInventarioResponseDTO = new LoteInventarioResponseDTO();
        loteInventarioResponseDTO.setIdLote(loteInventarioEntity.getIdLote());
        loteInventarioResponseDTO.setIdCompra(loteInventarioEntity.getCompra().getIdCompra());
        loteInventarioResponseDTO.setIdProducto(loteInventarioEntity.getProducto().getIdProducto());
        loteInventarioResponseDTO.setCantidadLote(loteInventarioEntity.getCantidadLote());
        loteInventarioResponseDTO.setPrecioUnitario(loteInventarioEntity.getPrecioUnitario());
        loteInventarioResponseDTO.setFechaEntrada(loteInventarioEntity.getFechaEntrada());
        return loteInventarioResponseDTO;
    }
}
