CREATE TABLE movimiento_inventario
(

    id_movimiento    SERIAL PRIMARY KEY,
    id_compra        INTEGER     NULL,
    id_producto      INTEGER     NOT NULL,
    id_venta         INTEGER     NULL,
    id_lote          INTEGER     NULL,
    cantidad         INTEGER     NOT NULL,
    tipo_movimiento  VARCHAR(20) NOT NULL,
    fecha_movimiento TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    motivo           VARCHAR(50) NOT NULL,

    CONSTRAINT fk_compra_movimiento FOREIGN KEY (id_compra) REFERENCES compra (id_compra),
    CONSTRAINT fk_producto_movimiento FOREIGN KEY (id_producto) REFERENCES producto (id_producto),
    CONSTRAINT fk_venta_movimiento FOREIGN KEY (id_venta) REFERENCES venta (id_venta),
    CONSTRAINT fk_lote_movimiento FOREIGN KEY (id_lote) REFERENCES lote_inventario (id_lote),
    CONSTRAINT chk_tipo_movimiento CHECK (tipo_movimiento IN ('ENTRADA', 'SALIDA')),
    CONSTRAINT chk_cantidad CHECK (cantidad > 0)
);