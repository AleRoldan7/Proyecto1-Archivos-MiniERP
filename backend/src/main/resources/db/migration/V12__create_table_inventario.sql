CREATE TABLE inventario
(
    id_inventario SERIAL PRIMARY KEY,
    id_producto   INTEGER UNIQUE NOT NULL,
    stock         INTEGER        NOT NULL,
    stock_minimo  INTEGER        NOT NULL,

    CONSTRAINT pk_id_producto FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);