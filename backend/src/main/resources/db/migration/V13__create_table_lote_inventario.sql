CREATE TABLE lote_inventario
(
    id_lote         SERIAL PRIMARY KEY,
    id_compra       INTEGER        NOT NULL,
    id_producto     INTEGER        NOT NULL,
    cantidad_lote   INTEGER        NOT NULL,
    precio_unitario NUMERIC(12, 2) NOT NULL,
    fecha_entrada   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_id_compra_inventario FOREIGN KEY (id_compra) REFERENCES compra (id_compra),
    CONSTRAINT fk_id_producto_inventario FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);