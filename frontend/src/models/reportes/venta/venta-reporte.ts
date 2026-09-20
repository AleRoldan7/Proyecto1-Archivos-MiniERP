export interface VentaReporte {
    idVenta: number;
    idCliente: number;
    nombreCliente: string;
    fechaVenta: string;
    subtotal: number;
    ivaVenta: number;
    total: number;
}