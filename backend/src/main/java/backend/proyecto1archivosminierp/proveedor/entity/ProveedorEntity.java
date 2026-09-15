package backend.proyecto1archivosminierp.proveedor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(name = "nombre_proveedor", nullable = false, unique = true, length = 150)
    private String nombreProveedor;

    @Column(name = "telefono_proveedor", nullable = false, unique = true, length = 15)
    private String telefonoProveedor;

    @Column(name = "correo_proveedor", nullable = false, unique = true, length = 50)
    private String correoProveedor;

    @Column(name = "direccion_proveedor", nullable = false, length = 200)
    private String direccionProveedor;

    @Column(name = "estado_proveedor", nullable = false)
    private Boolean estadoProveedor;
}
