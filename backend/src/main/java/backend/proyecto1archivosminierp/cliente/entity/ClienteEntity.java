package backend.proyecto1archivosminierp.cliente.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nombre", nullable = false, length = 250)
    private String nombre;

    @Column(name = "nit", nullable = false, unique = true, length = 10)
    private String nit;

    @Column(name = "dpi", nullable = false, unique = true, length = 13)
    private String dpi;

    @Column(name = "telefono", nullable = false, unique = true, length = 15)
    private String telefono;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
}
