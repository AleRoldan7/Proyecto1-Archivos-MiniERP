import { DetalleVentaResponse } from "./detalle-venta-response";

export interface VentaResponse {
    idVenta: number;
    idCliente: number;
    nombreCliente: number;
    fechaVenta: string;
    subtotal: number;
    ivaVenta: number;
    total: number;
    detalles: DetalleVentaResponse[];
}