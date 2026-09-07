package backend.proyecto1archivosminierp.producto.repository;

import backend.proyecto1archivosminierp.producto.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    boolean existsByNombreCategoria(String nombreCategoria);
}
