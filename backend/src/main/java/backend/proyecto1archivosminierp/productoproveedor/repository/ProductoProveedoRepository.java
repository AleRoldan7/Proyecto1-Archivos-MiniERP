package backend.proyecto1archivosminierp.productoproveedor.repository;

import backend.proyecto1archivosminierp.productoproveedor.entity.ProductoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoProveedoRepository extends JpaRepository<ProductoProveedorEntity, Integer> {

    List<ProductoProveedorEntity> findByProductoIdProductoAndEstadoTrue(Integer idProducto);

    List<ProductoProveedorEntity> findByProveedorIdProveedorAndEstadoTrue(Integer idProveedor);

    Optional<ProductoProveedorEntity> findByProductoIdProductoAndProveedorIdProveedor(Integer idProducto, Integer idProveedor);

    boolean existsByProductoIdProductoAndProveedorIdProveedor(Integer idProducto, Integer idProveedor);

    void deleteByProductoIdProducto(Integer idProducto);

    @Query("""
            SELECT pp FROM ProductoProveedorEntity pp
             JOIN FETCH pp.producto p
            WHERE pp.proveedor.idProveedor = :idProveedor
               AND pp.estado = true
               AND p.estadoProducto = true
            ORDER BY p.nombreProducto ASC
            """)
    List<ProductoProveedorEntity> buscarCatalogoPorProveedor(@Param("idProveedor") Integer idProveedor);
}
