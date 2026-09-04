package backend.proyecto1archivosminierp.producto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @OneToMany(mappedBy = "categoriaEntity")
    private List<ProductoEntity> productoEntities;
    private String nombreCategoria;
    private String descripcion;
}
