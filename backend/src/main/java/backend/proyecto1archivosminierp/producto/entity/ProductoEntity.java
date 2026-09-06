package backend.proyecto1archivosminierp.producto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "producto")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoriaEntity;

    private String descripcionProducto;
    private boolean estadoProducto;
}
