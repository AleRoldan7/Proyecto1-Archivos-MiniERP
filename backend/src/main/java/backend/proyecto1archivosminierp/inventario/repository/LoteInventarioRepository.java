package backend.proyecto1archivosminierp.inventario.repository;

import backend.proyecto1archivosminierp.inventario.entity.LoteInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteInventarioRepository extends JpaRepository<LoteInventarioEntity, Integer> {

    List<LoteInventarioEntity> findByProductoIdProductoOrderByFechaEntradaAscIdLoteAsc(Integer idProducto);
}
