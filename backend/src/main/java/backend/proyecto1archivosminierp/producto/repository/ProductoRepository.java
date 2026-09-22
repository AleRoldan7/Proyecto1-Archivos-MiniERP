package backend.proyecto1archivosminierp.producto.repository;

import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {

    boolean existsByNombreProductoIgnoreCase(String nombreProducto);

    boolean existsByNombreProductoIgnoreCaseAndIdProductoNot(String nombreProducto, Integer idProducto);

    List<ProductoEntity> findByEstadoProductoTrueOrderByNombreProductoAsc();

    long countByCategoriaIdCategoria(Integer idCategoria);

    boolean existsByCategoriaIdCategoriaAndEstadoProductoTrue(Integer idCategoria);

    @Query("""
            SELECT p FROM ProductoEntity p
            JOIN FETCH p.categoria c
            WHERE (:texto IS NULL OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :texto, '%')))
                AND (:idCategoria IS NULL OR c.idCategoria = :idCategoria) AND (:estado IS NULL OR p.estadoProducto = :estado)
            ORDER BY p.nombreProducto ASC
            """)
    List<ProductoEntity> buscar(@Param("texto") String texto, @Param("idCategoria") Integer idCategoria, @Param("estado") Boolean estado);

    @Query("""
            SELECT p FROM ProductoEntity p
             JOIN FETCH p.categoria c
             JOIN InventarioEntity i ON i.producto.idProducto = p.idProducto
            WHERE p.estadoProducto = true
               AND i.stock > 0
            ORDER BY p.nombreProducto ASC
            """)
    List<ProductoEntity> findDisponiblesParaVenta();
}
