package backend.proyecto1archivosminierp.reportes.repository.log;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import backend.proyecto1archivosminierp.reportes.entity.LogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
 /*
    @Query("""
            SELECT l FROM LogEntity l
            WHERE (:usuario IS NULL OR l.usuario = :usuario)
              AND (:modulo  IS NULL OR l.modulo  = :modulo)
              AND (:accion  IS NULL OR l.accionesLog  = :accion)
              AND (:desde   IS NULL OR l.fechaLog >= :desde)
              AND (:hasta   IS NULL OR l.fechaLog <= :hasta)
            ORDER BY l.fechaLog DESC
            """)
    List<LogEntity> buscar(@Param("usuario") String usuario, @Param("modulo") String modulo, @Param("accion") AccionesLog accion,
                           @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta, Pageable pageable);

  */

    @Query("""
            SELECT l FROM LogEntity l
            WHERE (:usuario IS NULL OR l.usuario = :usuario)
              AND (:modulo  IS NULL OR l.modulo  = :modulo)
              AND (:accion  IS NULL OR l.accionesLog  = :accion)
              AND l.fechaLog >= :desde
              AND l.fechaLog <= :hasta
            ORDER BY l.fechaLog DESC
            """)
    List<LogEntity> buscar(
            @Param("usuario") String usuario,
            @Param("modulo") String modulo,
            @Param("accion") AccionesLog accion,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Pageable pageable);

}
