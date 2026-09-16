-- ============================================================
-- V10__datos_prueba_ventas.sql
-- DATOS DE PRUEBA PARA MINIERP
-- ============================================================


-- ============================================================
-- 1. CATEGORÍA
-- ============================================================

INSERT INTO categoria (nombre_categoria,
                       descripcion)
VALUES ('Muebles',
        'Muebles para hogar y oficina');


-- ============================================================
-- 2. PRODUCTOS
-- ============================================================

INSERT INTO producto (nombre_producto,
                      id_categoria,
                      descripcion_producto,
                      estado_producto)
SELECT 'Silla de Oficina',
       id_categoria,
       'Silla ergonomica para oficina',
       TRUE
FROM categoria
WHERE nombre_categoria = 'Muebles';


INSERT INTO producto (nombre_producto,
                      id_categoria,
                      descripcion_producto,
                      estado_producto)
SELECT 'Escritorio Ejecutivo',
       id_categoria,
       'Escritorio de madera para oficina',
       TRUE
FROM categoria
WHERE nombre_categoria = 'Muebles';


-- ============================================================
-- 3. PROVEEDOR
-- ============================================================

INSERT INTO proveedor (nombre_proveedor,
                       telefono_proveedor,
                       correo_proveedor,
                       direccion_proveedor,
                       estado_proveedor)
VALUES ('Muebles Guatemala',
        '55551234',
        'ventas@mueblesgt.com',
        'Ciudad de Guatemala',
        TRUE);


-- ============================================================
-- 4. CLIENTE
-- ============================================================

INSERT INTO cliente (nombre,
                     nit,
                     dpi,
                     telefono,
                     correo,
                     direccion)
VALUES ('Juan Perez',
        '1234567-8',
        '1234567890101',
        '55550001',
        'juan.perez@gmail.com',
        'Ciudad de Guatemala');


-- ============================================================
-- 5. PRIMERA COMPRA
-- ============================================================

INSERT INTO compra (id_proveedor)
SELECT id_proveedor
FROM proveedor
WHERE nombre_proveedor = 'Muebles Guatemala';


-- ============================================================
-- 6. DETALLE DE LA PRIMERA COMPRA
-- ============================================================

INSERT INTO detalle_compra (id_compra,
                            id_producto,
                            cantidad,
                            precio_unitario)
SELECT c.id_compra,
       p.id_producto,
       10,
       500.00
FROM compra c
         JOIN proveedor pr
              ON pr.id_proveedor = c.id_proveedor
         JOIN producto p
              ON p.nombre_producto = 'Silla de Oficina'
WHERE pr.nombre_proveedor = 'Muebles Guatemala'
ORDER BY c.id_compra DESC
LIMIT 1;


-- ============================================================
-- 7. SEGUNDA COMPRA
-- ============================================================

INSERT INTO compra (id_proveedor)
SELECT id_proveedor
FROM proveedor
WHERE nombre_proveedor = 'Muebles Guatemala';


-- ============================================================
-- 8. DETALLE DE LA SEGUNDA COMPRA
-- ============================================================

INSERT INTO detalle_compra (id_compra,
                            id_producto,
                            cantidad,
                            precio_unitario)
SELECT c.id_compra,
       p.id_producto,
       8,
       550.00
FROM compra c
         JOIN proveedor pr
              ON pr.id_proveedor = c.id_proveedor
         JOIN producto p
              ON p.nombre_producto = 'Silla de Oficina'
WHERE pr.nombre_proveedor = 'Muebles Guatemala'
ORDER BY c.id_compra DESC
LIMIT 1;


-- ============================================================
-- 9. INVENTARIO
-- ============================================================

INSERT INTO inventario (id_producto,
                        stock,
                        stock_minimo)
SELECT p.id_producto,
       18,
       5
FROM producto p
WHERE p.nombre_producto = 'Silla de Oficina';


-- ============================================================
-- 10. PRIMER LOTE
-- ============================================================

INSERT INTO lote_inventario (id_compra,
                             id_producto,
                             cantidad_lote,
                             precio_unitario,
                             fecha_entrada)
SELECT dc.id_compra,
       dc.id_producto,
       dc.cantidad,
       dc.precio_unitario,
       c.fecha_compra
FROM detalle_compra dc
         JOIN compra c
              ON c.id_compra = dc.id_compra
         JOIN producto p
              ON p.id_producto = dc.id_producto
WHERE p.nombre_producto = 'Silla de Oficina'
  AND dc.cantidad = 10
ORDER BY dc.id_detalle
LIMIT 1;


-- ============================================================
-- 11. SEGUNDO LOTE
-- ============================================================

INSERT INTO lote_inventario (id_compra,
                             id_producto,
                             cantidad_lote,
                             precio_unitario,
                             fecha_entrada)
SELECT dc.id_compra,
       dc.id_producto,
       dc.cantidad,
       dc.precio_unitario,
       c.fecha_compra
FROM detalle_compra dc
         JOIN compra c
              ON c.id_compra = dc.id_compra
         JOIN producto p
              ON p.id_producto = dc.id_producto
WHERE p.nombre_producto = 'Silla de Oficina'
  AND dc.cantidad = 8
ORDER BY dc.id_detalle DESC
LIMIT 1;


-- ============================================================
-- FIN DE DATOS INICIALES
-- ============================================================