package backend.proyecto1archivosminierp.usuario.repository;

import backend.proyecto1archivosminierp.usuario.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUserName(String userName);

    Optional<UsuarioEntity> findByCorreoUsuario(String correoUsuario);

    boolean existsByUserName(String userName);

    boolean existsByCorreoUsuario(String correoUsuario);

}
