package backend.proyecto1archivosminierp.compra.repository;

import backend.proyecto1archivosminierp.compra.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Integer> {

    @Query("""
            SELECT COALESCE(SUM(dc.cantidad * dc.precioUnitario), 0)
            FROM DetalleCompraEntity dc
            WHERE dc.compra.fechaCompra >= :desde
            """)
    BigDecimal sumTotalDesde(@Param("desde") LocalDateTime desde);
}
