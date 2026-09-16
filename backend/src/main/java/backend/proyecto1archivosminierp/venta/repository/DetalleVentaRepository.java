package backend.proyecto1archivosminierp.venta.repository;

import backend.proyecto1archivosminierp.venta.entity.DetalleVentaEntity;
import backend.proyecto1archivosminierp.venta.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Integer> {

    List<DetalleVentaEntity> findByVenta(VentaEntity venta);

    List<DetalleVentaEntity> findByVentaIdVenta(Integer idVenta);
}
