CREATE TABLE producto_proveedor
(
    id_producto_proveedor SERIAL PRIMARY KEY,
    id_producto           INTEGER        NOT NULL,
    id_proveedor          INTEGER        NOT NULL,
    precio_compra         NUMERIC(12, 2) NOT NULL,
    estado                BOOLEAN        NOT NULL DEFAULT TRUE,
    fecha_creacion        TIMESTAMP      NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_prod_prov_producto FOREIGN KEY (id_producto) REFERENCES producto (id_producto),
    CONSTRAINT fk_prod_prov_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor (id_proveedor),
    CONSTRAINT uk_prod_prov UNIQUE (id_producto, id_proveedor),
    CONSTRAINT chk_pp_precio_positivo CHECK (precio_compra > 0)
);

CREATE INDEX idx_pp_producto ON producto_proveedor (id_producto);
CREATE INDEX idx_pp_proveedor ON producto_proveedor (id_proveedor);
CREATE INDEX idx_pp_estado ON producto_proveedor (estado);


INSERT INTO producto_proveedor (id_producto, id_proveedor, precio_compra)
VALUES (1, 1, 300.00),
       (1, 2, 320.00),
       (2, 1, 900.00),
       (3, 1, 150.00);