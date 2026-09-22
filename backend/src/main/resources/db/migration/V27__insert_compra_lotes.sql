DO
$$
    DECLARE
        v_id_compra INTEGER;
    BEGIN

        -- COMPRA 3: Proveedor 1 (Muebles Guatemala) - 3 productos
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (1, NOW() - INTERVAL '80 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 2, 3, 900.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 2, 3, 900.00, NOW() - INTERVAL '80 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 2;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 2, 3, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 3, 5, 750.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 3, 5, 750.00, NOW() - INTERVAL '80 days');
        UPDATE inventario SET stock = stock + 5 WHERE id_producto = 3;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 3, 5, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 4, 2, 600.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 4, 2, 600.00, NOW() - INTERVAL '80 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 4;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 4, 2, 'ENTRADA', 'COMPRA');

        -- COMPRA 4: Proveedor 2 (Maderas del Sur)
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (2, NOW() - INTERVAL '75 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 5, 8, 950.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 5, 8, 950.00, NOW() - INTERVAL '75 days');
        UPDATE inventario SET stock = stock + 8 WHERE id_producto = 5;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 5, 8, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 6, 5, 500.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 6, 5, 500.00, NOW() - INTERVAL '75 days');
        UPDATE inventario SET stock = stock + 5 WHERE id_producto = 6;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 6, 5, 'ENTRADA', 'COMPRA');

        -- COMPRA 5: Proveedor 3 (Mobiliario Moderno)
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (3, NOW() - INTERVAL '70 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 7, 10, 220.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 7, 10, 220.00, NOW() - INTERVAL '70 days');
        UPDATE inventario SET stock = stock + 10 WHERE id_producto = 7;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 7, 10, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 8, 3, 2000.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 8, 3, 2000.00, NOW() - INTERVAL '70 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 8;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 8, 3, 'ENTRADA', 'COMPRA');

        -- COMPRA 6: Proveedor 4
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (4, NOW() - INTERVAL '65 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 9, 5, 650.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 9, 5, 650.00, NOW() - INTERVAL '65 days');
        UPDATE inventario SET stock = stock + 5 WHERE id_producto = 9;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 9, 5, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 10, 8, 380.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 10, 8, 380.00, NOW() - INTERVAL '65 days');
        UPDATE inventario SET stock = stock + 8 WHERE id_producto = 10;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 10, 8, 'ENTRADA', 'COMPRA');

        -- COMPRA 7: Proveedor 5
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (5, NOW() - INTERVAL '60 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 11, 4, 300.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 11, 4, 300.00, NOW() - INTERVAL '60 days');
        UPDATE inventario SET stock = stock + 4 WHERE id_producto = 11;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 11, 4, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 12, 3, 900.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 12, 3, 900.00, NOW() - INTERVAL '60 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 12;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 12, 3, 'ENTRADA', 'COMPRA');

        -- COMPRA 8: Proveedor 6
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (6, NOW() - INTERVAL '55 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 13, 6, 420.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 13, 6, 420.00, NOW() - INTERVAL '55 days');
        UPDATE inventario SET stock = stock + 6 WHERE id_producto = 13;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 13, 6, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 14, 5, 550.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 14, 5, 550.00, NOW() - INTERVAL '55 days');
        UPDATE inventario SET stock = stock + 5 WHERE id_producto = 14;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 14, 5, 'ENTRADA', 'COMPRA');

        -- COMPRA 9: Proveedor 7
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (7, NOW() - INTERVAL '50 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 15, 4, 900.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 15, 4, 900.00, NOW() - INTERVAL '50 days');
        UPDATE inventario SET stock = stock + 4 WHERE id_producto = 15;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 15, 4, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 16, 3, 2800.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 16, 3, 2800.00, NOW() - INTERVAL '50 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 16;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 16, 3, 'ENTRADA', 'COMPRA');

        -- COMPRA 10: Proveedor 8
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (8, NOW() - INTERVAL '45 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 17, 2, 3000.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 17, 2, 3000.00, NOW() - INTERVAL '45 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 17;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 17, 2, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 18, 3, 2400.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 18, 3, 2400.00, NOW() - INTERVAL '45 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 18;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 18, 3, 'ENTRADA', 'COMPRA');

        -- COMPRA 11: Proveedor 1
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (1, NOW() - INTERVAL '40 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 19, 4, 2400.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 19, 4, 2400.00, NOW() - INTERVAL '40 days');
        UPDATE inventario SET stock = stock + 4 WHERE id_producto = 19;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 19, 4, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 20, 5, 700.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 20, 5, 700.00, NOW() - INTERVAL '40 days');
        UPDATE inventario SET stock = stock + 5 WHERE id_producto = 20;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 20, 5, 'ENTRADA', 'COMPRA');

        -- COMPRA 12: Proveedor 2
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (2, NOW() - INTERVAL '35 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 21, 4, 1100.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 21, 4, 1100.00, NOW() - INTERVAL '35 days');
        UPDATE inventario SET stock = stock + 4 WHERE id_producto = 21;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 21, 4, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 22, 2, 3100.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 22, 2, 3100.00, NOW() - INTERVAL '35 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 22;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 22, 2, 'ENTRADA', 'COMPRA');

        -- COMPRA 13: Proveedor 3
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (3, NOW() - INTERVAL '30 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 23, 3, 1600.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 23, 3, 1600.00, NOW() - INTERVAL '30 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 23;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 23, 3, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 24, 2, 450.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 24, 2, 450.00, NOW() - INTERVAL '30 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 24;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 24, 2, 'ENTRADA', 'COMPRA');

        -- COMPRA 14: Proveedor 4
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (4, NOW() - INTERVAL '25 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 25, 3, 1400.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 25, 3, 1400.00, NOW() - INTERVAL '25 days');
        UPDATE inventario SET stock = stock + 3 WHERE id_producto = 25;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 25, 3, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 26, 4, 1500.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 26, 4, 1500.00, NOW() - INTERVAL '25 days');
        UPDATE inventario SET stock = stock + 4 WHERE id_producto = 26;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 26, 4, 'ENTRADA', 'COMPRA');

        -- COMPRA 15: Proveedor 5
        INSERT INTO compra (id_proveedor, fecha_compra)
        VALUES (5, NOW() - INTERVAL '20 days')
        RETURNING id_compra INTO v_id_compra;

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 27, 2, 1100.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 27, 2, 1100.00, NOW() - INTERVAL '20 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 27;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 27, 2, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 28, 2, 2000.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 28, 2, 2000.00, NOW() - INTERVAL '20 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 28;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 28, 2, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 29, 2, 1000.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 29, 2, 1000.00, NOW() - INTERVAL '20 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 29;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 29, 2, 'ENTRADA', 'COMPRA');

        INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_unitario)
        VALUES (v_id_compra, 30, 2, 1800.00);
        INSERT INTO lote_inventario (id_compra, id_producto, cantidad_lote, precio_unitario, fecha_entrada)
        VALUES (v_id_compra, 30, 2, 1800.00, NOW() - INTERVAL '20 days');
        UPDATE inventario SET stock = stock + 2 WHERE id_producto = 30;
        INSERT INTO movimiento_inventario (id_compra, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_compra, 30, 2, 'ENTRADA', 'COMPRA');

    END
$$;