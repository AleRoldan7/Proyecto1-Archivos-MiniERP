package backend.proyecto1archivosminierp.proveedor.repository;

import backend.proyecto1archivosminierp.proveedor.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Integer> {

    boolean existsByNombreProveedor(String nombreProveedor);

    boolean existsByCorreoProveedor(String correoProveedor);

    boolean existsByTelefonoProveedor(String telefonoProveedor);
}
