package backend.proyecto1archivosminierp.usuario.repository;

import backend.proyecto1archivosminierp.usuario.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    Optional<RolEntity> findByNombreRol(String nombreRol);

    boolean existsByNombreRol(String nombreRol);
}
