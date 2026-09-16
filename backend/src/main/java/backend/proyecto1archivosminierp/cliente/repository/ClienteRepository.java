package backend.proyecto1archivosminierp.cliente.repository;

import backend.proyecto1archivosminierp.cliente.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByNit(String nit);

    Optional<ClienteEntity> findByDpi(String dpi);

    Optional<ClienteEntity> findByCorreo(String correo);

    boolean existsByNit(String nit);

    boolean existsByDpi(String dpi);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);
}
