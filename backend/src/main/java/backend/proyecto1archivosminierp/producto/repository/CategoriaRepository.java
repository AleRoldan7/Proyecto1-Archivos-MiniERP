package backend.proyecto1archivosminierp.producto.repository;

import backend.proyecto1archivosminierp.producto.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

    boolean existsByNombreCategoriaIgnoreCase(String nombreCategoria);

    boolean existsByNombreCategoriaIgnoreCaseAndIdCategoriaNot(String nombreCategoria, Integer idCategoria);
}
