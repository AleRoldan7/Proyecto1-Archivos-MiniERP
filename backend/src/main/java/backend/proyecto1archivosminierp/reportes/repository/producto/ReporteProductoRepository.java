package backend.proyecto1archivosminierp.reportes.repository.producto;

import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import backend.proyecto1archivosminierp.reportes.dto.producto.MovimientoHistorialDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoMovimientoDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoStockDTO;
import backend.proyecto1archivosminierp.reportes.dto.producto.ProductoVendidoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteProductoRepository extends JpaRepository<ProductoEntity, Long> {

    /*
    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoVendidoDTO(
                   p.idProducto, p.nombreProducto, SUM(dv.cantidad), SUM(dv.precioUnitario * dv.cantidad))
            FROM DetalleVentaEntity dv
            JOIN dv.producto p
            JOIN dv.venta v
            WHERE (:desde IS NULL OR v.fechaVenta >= :desde) AND (:hasta IS NULL OR v.fechaVenta <= :hasta)
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY SUM(dv.cantidad) DESC
            """)
    List<ProductoVendidoDTO> topProductosMasVendidos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoStockDTO(
                   i.idInventario, p.idProducto, p.nombreProducto, i.stock, i.stockMinimo)
            FROM InventarioEntity i
            JOIN i.producto p
            WHERE (:soloBajoMinimo = FALSE OR i.stock <= i.stockMinimo)
            ORDER BY i.stock ASC, p.nombreProducto ASC
            """)
    List<ProductoStockDTO> productosConMenorExistencia(@Param("soloBajoMinimo") boolean soloBajoMinimo, Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoMovimientoDTO(
                   p.idProducto, p.nombreProducto, COUNT(m), SUM(m.cantidad))
            FROM MovimientoInventarioEntity m
            JOIN m.producto p
            WHERE (:desde IS NULL OR m.fechaMovimiento >= :desde) AND (:hasta IS NULL OR m.fechaMovimiento <= :hasta)
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY COUNT(m) DESC
            """)
    List<ProductoMovimientoDTO> productosConMasMovimientos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);


    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.MovimientoHistorialDTO(
                   m.idMovimiento, p.idProducto, p.nombreProducto, m.tipoMovimiento,
                   m.cantidad, m.fechaMovimiento, m.motivo, l.idLote, v.idVenta)
            FROM MovimientoInventarioEntity m
            JOIN m.producto p
            LEFT JOIN m.lote l
            LEFT JOIN m.venta v
            WHERE p.idProducto = :idProducto AND (:desde IS NULL OR m.fechaMovimiento >= :desde)
            AND (:hasta IS NULL OR m.fechaMovimiento <= :hasta)
            ORDER BY m.fechaMovimiento DESC, m.idMovimiento DESC
            """)
    List<MovimientoHistorialDTO> historialDeProducto(@Param("idProducto") Long idProducto, @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    */

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoVendidoDTO(
                   p.idProducto, p.nombreProducto, SUM(dv.cantidad), SUM(dv.precioUnitario * dv.cantidad))
            FROM DetalleVentaEntity dv
            JOIN dv.producto p
            JOIN dv.venta v
            WHERE v.fechaVenta >= :desde AND v.fechaVenta <= :hasta
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY SUM(dv.cantidad) DESC
            """)
    List<ProductoVendidoDTO> topProductosMasVendidos(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoStockDTO(
                   i.idInventario, p.idProducto, p.nombreProducto, i.stock, i.stockMinimo)
            FROM InventarioEntity i
            JOIN i.producto p
            WHERE (:soloBajoMinimo = FALSE OR i.stock <= i.stockMinimo)
            ORDER BY i.stock ASC, p.nombreProducto ASC
            """)
    List<ProductoStockDTO> productosConMenorExistencia(@Param("soloBajoMinimo") boolean soloBajoMinimo, Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.ProductoMovimientoDTO(
                   p.idProducto, p.nombreProducto, COUNT(m), SUM(m.cantidad))
            FROM MovimientoInventarioEntity m
            JOIN m.producto p
            WHERE m.fechaMovimiento >= :desde AND m.fechaMovimiento <= :hasta
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY COUNT(m) DESC
            """)
    List<ProductoMovimientoDTO> productosConMasMovimientos(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.producto.MovimientoHistorialDTO(
                   m.idMovimiento, p.idProducto, p.nombreProducto, m.tipoMovimiento,
                   m.cantidad, m.fechaMovimiento, m.motivo, l.idLote, v.idVenta)
            FROM MovimientoInventarioEntity m
            JOIN m.producto p
            LEFT JOIN m.lote l
            LEFT JOIN m.venta v
            WHERE p.idProducto = :idProducto
              AND m.fechaMovimiento >= :desde
              AND m.fechaMovimiento <= :hasta
            ORDER BY m.fechaMovimiento DESC, m.idMovimiento DESC
            """)
    List<MovimientoHistorialDTO> historialDeProducto(
            @Param("idProducto") Long idProducto,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta);
}

