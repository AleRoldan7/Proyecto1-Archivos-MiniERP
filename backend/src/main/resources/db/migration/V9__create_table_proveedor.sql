CREATE TABLE proveedor
(
    id_proveedor        SERIAL PRIMARY KEY,
    nombre_proveedor    VARCHAR(150) UNIQUE NOT NULL,
    telefono_proveedor  VARCHAR(15) UNIQUE  NOT NULL,
    correo_proveedor    VARCHAR(50) UNIQUE  NOT NULL,
    direccion_proveedor VARCHAR(200)        NOT NULL,
    estado_proveedor    BOOLEAN             NOT NULL DEFAULT TRUE
);