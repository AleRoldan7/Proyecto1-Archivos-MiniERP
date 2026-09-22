package backend.proyecto1archivosminierp.venta.repository;

import backend.proyecto1archivosminierp.venta.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

    @Query("SELECT SUM(v.total) FROM VentaEntity v WHERE v.fechaVenta >= :desde")
    BigDecimal sumTotalDesde(@Param("desde") LocalDateTime desde);
}
