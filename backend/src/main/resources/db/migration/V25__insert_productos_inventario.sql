INSERT INTO producto (nombre_producto, id_categoria, descripcion_producto, estado_producto, precio_venta)
VALUES ('Escritorio Juvenil', 1, 'Escritorio para estudiantes', true, 900.00),
       ('Escritorio en L', 1, 'Escritorio en forma de L', true, 2200.00),
       ('Escritorio Infantil', 1, 'Escritorio para niños', true, 700.00),
       ('Silla Ergonómica', 1, 'Silla con soporte lumbar', true, 1200.00),
       ('Silla de Recepción', 1, 'Silla para visitas', true, 650.00),
       ('Taburete de Oficina', 1, 'Taburete ajustable', true, 280.00),
       ('Mesa de Comedor 6 personas', 1, 'Mesa de comedor grande', true, 2500.00),
       ('Mesa de Centro', 1, 'Mesa para sala', true, 800.00),
       ('Mesa de Noche', 1, 'Mesa pequeña para dormitorio', true, 450.00),
       ('Banco de Bar', 1, 'Banco alto para barra', true, 350.00),
       ('Mesita de TV', 1, 'Mesita para televisión', true, 550.00),
       ('Estantería Metálica', 1, 'Estantería de metal', true, 700.00),
       ('Estantería de Madera', 1, 'Estantería de madera', true, 950.00),
       ('Librero Modular', 1, 'Librero con módulos', true, 1100.00),
       ('Sofá de 3 Plazas', 1, 'Sofá grande para sala', true, 3500.00),
       ('Sofá de 2 Plazas', 1, 'Sofá mediano', true, 2800.00),
       ('Sofá Cama', 1, 'Sofá que se convierte en cama', true, 4200.00),
       ('Sillón Reclinable', 1, 'Sillón reclinable', true, 2900.00),
       ('Archivador Metálico', 1, 'Archivador de metal', true, 850.00),
       ('Archivador de Madera', 1, 'Archivador de madera', true, 990.00),
       ('Credenza', 1, 'Credenza para oficina', true, 1400.00),
       ('Mostrador de Recepción', 1, 'Mostrador para recepción', true, 3800.00),
       ('Vitrina de Exhibición', 1, 'Vitrina de vidrio', true, 2100.00),
       ('Gavetero', 1, 'Gavetero de oficina', true, 600.00),
       ('Cama Individual', 1, 'Cama individual', true, 1800.00),
       ('Cama Matrimonial', 1, 'Cama matrimonial', true, 2600.00),
       ('Cómoda', 1, 'Cómoda para dormitorio', true, 1300.00),
       ('Closet 3 Puertas', 1, 'Closet con 3 puertas', true, 2400.00);

INSERT INTO inventario (id_producto, stock, stock_minimo)
SELECT p.id_producto, 0, 5
FROM producto p
WHERE NOT EXISTS (SELECT 1 FROM inventario i WHERE i.id_producto = p.id_producto);