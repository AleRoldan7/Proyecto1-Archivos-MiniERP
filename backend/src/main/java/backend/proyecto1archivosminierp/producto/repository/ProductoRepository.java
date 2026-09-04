package backend.proyecto1archivosminierp.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

}
