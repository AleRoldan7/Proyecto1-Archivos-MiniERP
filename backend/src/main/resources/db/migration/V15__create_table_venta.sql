CREATE TABLE venta
(
    id_venta    SERIAL PRIMARY KEY,
    id_cliente  INTEGER        NOT NULL,
    fecha_venta TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal    NUMERIC(12, 2) NOT NULL,
    iva_venta   NUMERIC(12, 2) NOT NULL,
    total       NUMERIC(12, 2) NOT NULL,

    CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
);