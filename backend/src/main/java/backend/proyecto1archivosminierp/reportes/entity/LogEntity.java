package backend.proyecto1archivosminierp.reportes.entity;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_sistema")
@Getter
@Setter
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "modulo", length = 60, nullable = false)
    private String modulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion", length = 20, nullable = false)
    private AccionesLog accionesLog;

    @Column(name = "operacion", length = 120)
    private String operacion;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso;

    @Column(name = "fecha_log", nullable = false)
    private LocalDateTime fechaLog;
}
