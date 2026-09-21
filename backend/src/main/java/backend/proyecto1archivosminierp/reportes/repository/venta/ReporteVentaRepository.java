package backend.proyecto1archivosminierp.reportes.repository.venta;

import backend.proyecto1archivosminierp.reportes.dto.venta.ClienteTopDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ProductoIngresadoDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO;
import backend.proyecto1archivosminierp.reportes.dto.venta.VentaReporteDTO;
import backend.proyecto1archivosminierp.venta.entity.VentaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteVentaRepository extends JpaRepository<VentaEntity, Integer> {

    /*
        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.VentaReporteDTO(
                       v.idVenta, cl.idCliente, cl.nombre, v.fechaVenta,
                       v.subtotal, v.ivaVenta, v.total)
                FROM VentaEntity v
                JOIN v.cliente cl
                WHERE v.fechaVenta BETWEEN :desde AND :hasta
                ORDER BY v.fechaVenta DESC
                """)
        List<VentaReporteDTO> ventasPorRango(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ClienteTopDTO(
                       cl.idCliente, cl.nombre, COUNT(v), SUM(v.total))
                FROM VentaEntity v
                JOIN v.cliente cl
                WHERE (:desde IS NULL OR v.fechaVenta >= :desde) AND (:hasta IS NULL OR v.fechaVenta <= :hasta)
                GROUP BY cl.idCliente, cl.nombre
                ORDER BY SUM(v.total) DESC
                """)
        List<ClienteTopDTO> topClientesPorMonto(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ProductoIngresadoDTO(
                       p.idProducto, p.nombreProducto, SUM(dv.cantidad), SUM(dv.precioUnitario * dv.cantidad))
                FROM DetalleVentaEntity dv
                JOIN dv.producto p
                JOIN dv.venta v
                WHERE (:desde IS NULL OR v.fechaVenta >= :desde) AND (:hasta IS NULL OR v.fechaVenta <= :hasta)
                GROUP BY p.idProducto, p.nombreProducto
                ORDER BY SUM(dv.precioUnitario * dv.cantidad) DESC
                """)
        List<ProductoIngresadoDTO> topProductosPorIngreso(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                       YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta), COUNT(v), SUM(v.total))
                FROM VentaEntity v
                WHERE v.fechaVenta BETWEEN :desde AND :hasta
                GROUP BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
                ORDER BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
                """)
        List<ResumenVentaDTO> resumenDiario(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                       YEAR(v.fechaVenta), WEEK(v.fechaVenta), COUNT(v), SUM(v.total))
                FROM VentaEntity v
                WHERE v.fechaVenta BETWEEN :desde AND :hasta
                GROUP BY YEAR(v.fechaVenta), WEEK(v.fechaVenta)
                ORDER BY YEAR(v.fechaVenta), WEEK(v.fechaVenta)
                """)
        List<ResumenVentaDTO> resumenSemanal(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                       YEAR(v.fechaVenta), MONTH(v.fechaVenta), COUNT(v), SUM(v.total))
                FROM VentaEntity v
                WHERE v.fechaVenta BETWEEN :desde AND :hasta
                GROUP BY YEAR(v.fechaVenta), MONTH(v.fechaVenta)
                ORDER BY YEAR(v.fechaVenta), MONTH(v.fechaVenta)
                """)
        List<ResumenVentaDTO> resumenMensual(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

        @Query("""
                SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                       YEAR(v.fechaVenta), COUNT(v), SUM(v.total))
                FROM VentaEntity v
                WHERE v.fechaVenta BETWEEN :desde AND :hasta
                GROUP BY YEAR(v.fechaVenta)
                ORDER BY YEAR(v.fechaVenta)
                """)
        List<ResumenVentaDTO> resumenAnual(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

     */

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.VentaReporteDTO(
                   v.idVenta, cl.idCliente, cl.nombre, v.fechaVenta,
                   v.subtotal, v.ivaVenta, v.total)
            FROM VentaEntity v
            JOIN v.cliente cl
            WHERE v.fechaVenta BETWEEN :desde AND :hasta
            ORDER BY v.fechaVenta DESC
            """)
    List<VentaReporteDTO> ventasPorRango(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);


    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ClienteTopDTO(
                   cl.idCliente, cl.nombre, COUNT(v), SUM(v.total))
            FROM VentaEntity v
            JOIN v.cliente cl
            WHERE v.fechaVenta >= :desde AND v.fechaVenta <= :hasta
            GROUP BY cl.idCliente, cl.nombre
            ORDER BY SUM(v.total) DESC
            """)
    List<ClienteTopDTO> topClientesPorMonto(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ProductoIngresadoDTO(
                   p.idProducto, p.nombreProducto, SUM(dv.cantidad), SUM(dv.precioUnitario * dv.cantidad))
            FROM DetalleVentaEntity dv
            JOIN dv.producto p
            JOIN dv.venta v
            WHERE v.fechaVenta >= :desde AND v.fechaVenta <= :hasta
            GROUP BY p.idProducto, p.nombreProducto
            ORDER BY SUM(dv.precioUnitario * dv.cantidad) DESC
            """)
    List<ProductoIngresadoDTO> topProductosPorIngreso(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                   YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta), COUNT(v), SUM(v.total))
            FROM VentaEntity v
            WHERE v.fechaVenta BETWEEN :desde AND :hasta
            GROUP BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
            ORDER BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
            """)
    List<ResumenVentaDTO> resumenDiario(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                   YEAR(v.fechaVenta), WEEK(v.fechaVenta), COUNT(v), SUM(v.total))
            FROM VentaEntity v
            WHERE v.fechaVenta BETWEEN :desde AND :hasta
            GROUP BY YEAR(v.fechaVenta), WEEK(v.fechaVenta)
            ORDER BY YEAR(v.fechaVenta), WEEK(v.fechaVenta)
            """)
    List<ResumenVentaDTO> resumenSemanal(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                   YEAR(v.fechaVenta), MONTH(v.fechaVenta), COUNT(v), SUM(v.total))
            FROM VentaEntity v
            WHERE v.fechaVenta BETWEEN :desde AND :hasta
            GROUP BY YEAR(v.fechaVenta), MONTH(v.fechaVenta)
            ORDER BY YEAR(v.fechaVenta), MONTH(v.fechaVenta)
            """)
    List<ResumenVentaDTO> resumenMensual(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("""
            SELECT new backend.proyecto1archivosminierp.reportes.dto.venta.ResumenVentaDTO(
                   YEAR(v.fechaVenta), COUNT(v), SUM(v.total))
            FROM VentaEntity v
            WHERE v.fechaVenta BETWEEN :desde AND :hasta
            GROUP BY YEAR(v.fechaVenta)
            ORDER BY YEAR(v.fechaVenta)
            """)
    List<ResumenVentaDTO> resumenAnual(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}
