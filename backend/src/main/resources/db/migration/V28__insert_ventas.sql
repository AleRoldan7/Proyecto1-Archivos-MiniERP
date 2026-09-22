DO
$$
    DECLARE
        v_id_venta    INTEGER;
        v_id_producto INTEGER;
        v_cantidad    INTEGER;
        v_precio      NUMERIC(12, 2);
        v_subtotal    NUMERIC(12, 2);
        v_iva         NUMERIC(12, 2);
        v_total       NUMERIC(12, 2);
    BEGIN

        -- VENTA 1: Cliente 1, Producto 1 (Silla de Oficina) x2 @500
        v_id_producto := 1; v_cantidad := 2; v_precio := 500.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (1, NOW() - INTERVAL '20 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 2: Cliente 2, Producto 2 (Escritorio Ejecutivo) x1 @1500
        v_id_producto := 2; v_cantidad := 1; v_precio := 1500.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (2, NOW() - INTERVAL '19 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 3: Cliente 3, Producto 7 x2 @280
        v_id_producto := 7; v_cantidad := 2; v_precio := 280.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (3, NOW() - INTERVAL '18 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 4: Cliente 4, Producto 8 x3 @2500
        v_id_producto := 8; v_cantidad := 3; v_precio := 2500.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (4, NOW() - INTERVAL '17 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 5: Cliente 5, Producto 9 x2 @800
        v_id_producto := 9; v_cantidad := 2; v_precio := 800.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (5, NOW() - INTERVAL '16 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 6: Cliente 6, Producto 10 x3 @450
        v_id_producto := 10; v_cantidad := 3; v_precio := 450.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (6, NOW() - INTERVAL '15 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 7: Cliente 7, Producto 11 x4 @350
        v_id_producto := 11; v_cantidad := 4; v_precio := 350.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (7, NOW() - INTERVAL '14 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 8: Cliente 8, Producto 12 x2 @550
        v_id_producto := 12; v_cantidad := 2; v_precio := 550.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (8, NOW() - INTERVAL '13 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 9: Cliente 9, Producto 13 x1 @700
        v_id_producto := 13; v_cantidad := 1; v_precio := 700.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (9, NOW() - INTERVAL '12 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 10: Cliente 10, Producto 14 x2 @950
        v_id_producto := 14; v_cantidad := 2; v_precio := 950.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (10, NOW() - INTERVAL '11 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 11: Cliente 11, Producto 15 x1 @1100
        v_id_producto := 15; v_cantidad := 1; v_precio := 1100.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (11, NOW() - INTERVAL '10 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 12: Cliente 12, Producto 16 x1 @3500
        v_id_producto := 16; v_cantidad := 1; v_precio := 3500.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (12, NOW() - INTERVAL '9 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 13: Cliente 13, Producto 17 x1 @2800
        v_id_producto := 17; v_cantidad := 1; v_precio := 2800.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (13, NOW() - INTERVAL '8 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 14: Cliente 14, Producto 18 x2 @2900
        v_id_producto := 18; v_cantidad := 2; v_precio := 2900.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (14, NOW() - INTERVAL '7 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 15: Cliente 15, Producto 19 x3 @850
        v_id_producto := 19; v_cantidad := 3; v_precio := 850.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (15, NOW() - INTERVAL '6 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 16: Cliente 16, Producto 20 x2 @990
        v_id_producto := 20; v_cantidad := 2; v_precio := 990.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (16, NOW() - INTERVAL '5 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 17: Cliente 17, Producto 21 x1 @1400
        v_id_producto := 21; v_cantidad := 1; v_precio := 1400.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (17, NOW() - INTERVAL '4 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 18: Cliente 18, Producto 22 x1 @3800
        v_id_producto := 22; v_cantidad := 1; v_precio := 3800.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (18, NOW() - INTERVAL '3 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 19: Cliente 19, Producto 23 x2 @2100
        v_id_producto := 23; v_cantidad := 2; v_precio := 2100.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (19, NOW() - INTERVAL '3 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 20: Cliente 20, Producto 24 x3 @600
        v_id_producto := 24; v_cantidad := 3; v_precio := 600.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (20, NOW() - INTERVAL '2 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 21: Cliente 1, Producto 25 x1 @1800
        v_id_producto := 25; v_cantidad := 1; v_precio := 1800.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (1, NOW() - INTERVAL '2 days', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 22: Cliente 2, Producto 26 x1 @2600
        v_id_producto := 26; v_cantidad := 1; v_precio := 2600.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (2, NOW() - INTERVAL '1 day', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 23: Cliente 3, Producto 27 x2 @1300
        v_id_producto := 27; v_cantidad := 2; v_precio := 1300.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (3, NOW() - INTERVAL '1 day', v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 24: Cliente 4, Producto 28 x1 @2400
        v_id_producto := 28; v_cantidad := 1; v_precio := 2400.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (4, NOW(), v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

        -- VENTA 25: Cliente 5, Producto 29 x2 @1300
        v_id_producto := 29; v_cantidad := 2; v_precio := 1300.00;
        v_subtotal := v_cantidad * v_precio;
        v_iva := v_subtotal * 0.12;
        v_total := v_subtotal + v_iva;
        INSERT INTO venta (id_cliente, fecha_venta, subtotal, iva_venta, total)
        VALUES (5, NOW(), v_subtotal, v_iva, v_total)
        RETURNING id_venta INTO v_id_venta;
        INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
        VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio);
        UPDATE inventario SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        INSERT INTO movimiento_inventario (id_venta, id_producto, cantidad, tipo_movimiento, motivo)
        VALUES (v_id_venta, v_id_producto, v_cantidad, 'SALIDA', 'VENTA');

    END
$$;