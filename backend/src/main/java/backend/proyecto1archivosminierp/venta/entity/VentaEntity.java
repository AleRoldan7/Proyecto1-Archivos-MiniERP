package backend.proyecto1archivosminierp.venta.entity;

import backend.proyecto1archivosminierp.cliente.entity.ClienteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Getter
@Setter
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_id_cliente"))
    private ClienteEntity cliente;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "iva_venta", nullable = false, precision = 12, scale = 2)
    private BigDecimal ivaVenta;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
}
