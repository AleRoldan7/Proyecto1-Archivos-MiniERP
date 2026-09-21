package backend.proyecto1archivosminierp.reportes.repository.compra;

import backend.proyecto1archivosminierp.compra.entity.CompraEntity;
import backend.proyecto1archivosminierp.reportes.dto.compra.CompraReporteDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProductoCompradoDTO;
import backend.proyecto1archivosminierp.reportes.dto.compra.ProveedorTopDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteCompraRepository extends JpaRepository<CompraEntity, Integer> {

    /*
    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.CompraReporteDTO(
                        c.idCompra, pr.idProveedor, pr.nombreProveedor, c.fechaCompra,SUM(d.cantidad), SUM(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN c.proveedor pr
            WHERE c.fechaCompra BETWEEN :desde AND :hasta
            GROUP BY c.idCompra, pr.idProveedor, pr.nombreProveedor, c.fechaCompra
            ORDER BY c.fechaCompra DESC
            """)
    List<CompraReporteDTO> comprasPorRango(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.ProveedorTopDTO(
                   pr.idProveedor, pr.nombreProveedor, count(DISTINCT c.idCompra), sum(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN c.proveedor pr
            WHERE (:desde IS NULL OR c.fechaCompra >= :desde) AND (:hasta IS NULL OR c.fechaCompra <= :hasta)
            GROUP BY pr.idProveedor, pr.nombreProveedor
            ORDER BY  sum(d.precioUnitario * d.cantidad) DESC
            """)
    List<ProveedorTopDTO> topProveedoresPorMonto(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.ProductoCompradoDTO(
                   p.idProducto, p.nombreProducto, COUNT(DISTINCT c.idCompra), SUM(d.cantidad), SUM(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN d.producto p
            WHERE (:desde IS NULL OR c.fechaCompra >= :desde) AND (:hasta IS NULL OR c.fechaCompra <= :hasta)
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY COUNT(DISTINCT c.idCompra) DESC, SUM(d.cantidad) DESC
            """)
    List<ProductoCompradoDTO> productosMasAdquiridos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

     */

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.CompraReporteDTO(
                        c.idCompra, pr.idProveedor, pr.nombreProveedor, c.fechaCompra,SUM(d.cantidad), SUM(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN c.proveedor pr
            WHERE c.fechaCompra BETWEEN :desde AND :hasta
            GROUP BY c.idCompra, pr.idProveedor, pr.nombreProveedor, c.fechaCompra
            ORDER BY c.fechaCompra DESC
            """)
    List<CompraReporteDTO> comprasPorRango(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.ProveedorTopDTO(
                   pr.idProveedor, pr.nombreProveedor, count(DISTINCT c.idCompra), sum(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN c.proveedor pr
            WHERE c.fechaCompra >= :desde AND c.fechaCompra <= :hasta
            GROUP BY pr.idProveedor, pr.nombreProveedor
            ORDER BY sum(d.precioUnitario * d.cantidad) DESC
            """)
    List<ProveedorTopDTO> topProveedoresPorMonto(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.compra.ProductoCompradoDTO(
                   p.idProducto, p.nombreProducto, COUNT(DISTINCT c.idCompra), SUM(d.cantidad), SUM(d.precioUnitario * d.cantidad))
            FROM DetalleCompraEntity d
            JOIN d.compra c
            JOIN d.producto p
            WHERE c.fechaCompra >= :desde AND c.fechaCompra <= :hasta
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY COUNT(DISTINCT c.idCompra) DESC, SUM(d.cantidad) DESC
            """)
    List<ProductoCompradoDTO> productosMasAdquiridos(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);
}
