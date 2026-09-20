import { DetalleRequest } from "./detalle-request";

export interface CompraRequest {
    idProveedor: number;
    detalles: DetalleRequest[];
}