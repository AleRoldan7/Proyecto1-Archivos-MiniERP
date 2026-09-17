CREATE TABLE log_sistema
(

    id_log      SERIAL PRIMARY KEY,
    usuario     VARCHAR(100) NULL,
    modulo      VARCHAR(60)  NOT NULL,
    accion      VARCHAR(20)  NOT NULL,
    operacion   VARCHAR(120) NULL,
    descripcion VARCHAR(500) NULL,
    exitoso     BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_log   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_accion CHECK (accion IN ('CREAR', 'ACTUALIZAR', 'CONSULTAR', 'ELIMINAR', 'ERROR'))
)