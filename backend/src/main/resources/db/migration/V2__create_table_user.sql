CREATE TABLE rol
(
    id_rol          BIGSERIAL PRIMARY KEY,
    nombre_rol      VARCHAR(50) NOT NULL UNIQUE,
    descripcion_rol VARCHAR(150)
);

CREATE TABLE usuario
(
    id_usuario       BIGSERIAL PRIMARY KEY,
    nombre_usuario   VARCHAR(150),
    correo_usuario   VARCHAR(40),
    telefono_usuario CHAR(12),
    estado_usuario   BOOLEAN DEFAULT TRUE,
    id_rol           INTEGER,
    user_name        VARCHAR(50),
    password         VARCHAR(50),
    FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
);