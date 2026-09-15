CREATE TABLE detalle_venta
(
    id_detalle_venta SERIAL PRIMARY KEY,
    id_venta INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_id_venta FOREIGN KEY (id_venta) REFERENCES venta (id_venta),
    CONSTRAINT fk_id_producto_venta FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);