import { DetalleResponse } from "./detalle-response";

export interface CompraResponse {
    idCompra: number;
    idProveedor: number;
    fechaCompra: string;
    detalles: DetalleResponse[];
}