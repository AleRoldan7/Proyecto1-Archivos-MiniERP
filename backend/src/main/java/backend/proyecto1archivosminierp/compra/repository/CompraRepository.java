package backend.proyecto1archivosminierp.compra.repository;

import backend.proyecto1archivosminierp.compra.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Integer> {
}
