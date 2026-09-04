CREATE TABLE categoria
(
    id_categoria     SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion      TEXT
);


CREATE TABLE producto
(
    id_producto     SERIAL PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    id_categoria INTEGER,
    descripcion_producto TEXT,
    estado_producto BOOLEAN,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)


);

