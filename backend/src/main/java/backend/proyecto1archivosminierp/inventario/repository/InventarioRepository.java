package backend.proyecto1archivosminierp.inventario.repository;

import backend.proyecto1archivosminierp.inventario.entity.InventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioEntity, Integer> {

    Optional<InventarioEntity> findByProductoIdProducto(Integer idProducto);

}
