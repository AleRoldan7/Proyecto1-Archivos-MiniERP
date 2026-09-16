package backend.proyecto1archivosminierp.venta.repository;

import backend.proyecto1archivosminierp.venta.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {
}
