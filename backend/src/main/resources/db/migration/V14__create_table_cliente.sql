CREATE TABLE cliente
(
    id_cliente SERIAL PRIMARY KEY,
    nombre     VARCHAR(250)        NOT NULL,
    nit        VARCHAR(10) UNIQUE  NOT NULL,
    dpi        VARCHAR(13) UNIQUE  NOT NULL,
    telefono   VARCHAR(15) UNIQUE  NOT NULL,
    correo     VARCHAR(100) UNIQUE NOT NULL,
    direccion  VARCHAR(200)        NOT NULL
);