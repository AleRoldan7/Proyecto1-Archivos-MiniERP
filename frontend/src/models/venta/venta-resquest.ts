import { DetalleVentaRequest } from "./detalle-venta-request";

export interface VentaRequest {
    idCliente: number;
    detalles: DetalleVentaRequest[];
}