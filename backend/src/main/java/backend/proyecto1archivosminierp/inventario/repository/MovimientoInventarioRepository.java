package backend.proyecto1archivosminierp.inventario.repository;

import backend.proyecto1archivosminierp.inventario.entity.MovimientoInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventarioEntity, Integer> {

    List<MovimientoInventarioEntity> findByLoteIdLote(Integer idLote);

    List<MovimientoInventarioEntity> findByVentaIdVenta(Integer idVenta);
}
