package backend.proyecto1archivosminierp.compra.repository;

import backend.proyecto1archivosminierp.compra.entity.DetalleCompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompraEntity, Integer> {

    List<DetalleCompraEntity> findByCompraIdCompra(Integer idCompra);
}
