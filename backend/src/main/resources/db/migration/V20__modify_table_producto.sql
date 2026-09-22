ALTER TABLE producto
    ADD COLUMN precio_venta NUMERIC(12, 2) NOT NULL DEFAULT 0;

ALTER TABLE producto
    ADD CONSTRAINT chk_producto_precio_positivo CHECK (precio_venta >= 0);

UPDATE producto
SET precio_venta = 500.00
WHERE id_producto = 1;
UPDATE producto
SET precio_venta = 1500.00
WHERE id_producto = 2;