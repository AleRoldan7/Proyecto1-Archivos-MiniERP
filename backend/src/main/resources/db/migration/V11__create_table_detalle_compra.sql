CREATE TABLE detalle_compra
(
    id_detalle      SERIAL PRIMARY KEY,
    id_compra       INTEGER        NOT NULL,
    id_producto     INTEGER        NOT NULL,
    cantidad        INTEGER        NOT NULL,
    precio_unitario NUMERIC(12, 2) NOT NULL,

    CONSTRAINT fk_id_compra FOREIGN KEY (id_compra) REFERENCES compra (id_compra),
    CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);