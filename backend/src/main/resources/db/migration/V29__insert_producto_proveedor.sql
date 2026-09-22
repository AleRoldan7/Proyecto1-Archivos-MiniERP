INSERT INTO producto_proveedor (id_producto, id_proveedor, precio_compra, estado)
SELECT p.id_producto, prov.id_proveedor, v.precio_compra, TRUE
FROM (VALUES ('Escritorio Juvenil', 1, 600.00),
             ('Escritorio Juvenil', 2, 620.00),
             ('Escritorio en L', 1, 1500.00),
             ('Escritorio en L', 3, 1450.00),
             ('Escritorio Infantil', 1, 450.00),
             ('Silla Ergonómica', 1, 800.00),
             ('Silla Ergonómica', 2, 780.00),
             ('Silla de Recepción', 1, 420.00),
             ('Taburete de Oficina', 1, 180.00),
             ('Mesa de Comedor 6 personas', 1, 1700.00),
             ('Mesa de Comedor 6 personas', 3, 1650.00),
             ('Mesa de Centro', 1, 520.00),
             ('Mesa de Noche', 2, 300.00),
             ('Banco de Bar', 1, 220.00),
             ('Mesita de TV', 1, 360.00),
             ('Estantería Metálica', 1, 450.00),
             ('Estantería Metálica', 2, 460.00),
             ('Estantería de Madera', 1, 620.00),
             ('Librero Modular', 1, 750.00),
             ('Librero Modular', 3, 730.00),
             ('Sofá de 3 Plazas', 1, 2300.00),
             ('Sofá de 3 Plazas', 2, 2350.00),
             ('Sofá de 2 Plazas', 1, 1850.00),
             ('Sofá Cama', 1, 2800.00),
             ('Sillón Reclinable', 1, 1900.00),
             ('Sillón Reclinable', 2, 1950.00),
             ('Archivador Metálico', 1, 550.00),
             ('Archivador de Madera', 1, 640.00),
             ('Credenza', 1, 900.00),
             ('Mostrador de Recepción', 1, 2500.00),
             ('Mostrador de Recepción', 3, 2450.00),
             ('Vitrina de Exhibición', 1, 1400.00),
             ('Gavetero', 1, 380.00),
             ('Cama Individual', 1, 1200.00),
             ('Cama Matrimonial', 1, 1700.00),
             ('Cama Matrimonial', 3, 1680.00),
             ('Cómoda', 2, 850.00),
             ('Closet 3 Puertas', 1, 1600.00)) AS v(nombre_producto, id_proveedor, precio_compra)
         JOIN producto p ON p.nombre_producto = v.nombre_producto
         JOIN proveedor prov ON prov.id_proveedor = v.id_proveedor
WHERE NOT EXISTS (SELECT 1
                  FROM producto_proveedor pp
                  WHERE pp.id_producto = p.id_producto
                    AND pp.id_proveedor = prov.id_proveedor);