package backend.proyecto1archivosminierp.inventario.entity;

import backend.proyecto1archivosminierp.compra.entity.CompraEntity;
import backend.proyecto1archivosminierp.producto.entity.ProductoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote_inventario")
@Getter
@Setter
public class LoteInventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false, foreignKey = @ForeignKey(name = "fk_id_compra_inventario"))
    private CompraEntity compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "fk_id_producto_inventario"))
    private ProductoEntity producto;

    @Column(name = "cantidad_lote", nullable = false)
    private Integer cantidadLote;

    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;
}
