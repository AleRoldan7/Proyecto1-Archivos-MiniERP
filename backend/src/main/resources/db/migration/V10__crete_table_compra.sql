CREATE TABLE compra
(
    id_compra    SERIAL PRIMARY KEY,
    id_proveedor INTEGER   NOT NULL,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_id_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor (id_proveedor)
);

