export interface MovimientoHistorial {
    idMovimiento: number;
    idProducto: number;
    nombreProducto: string;
    tipoMovimiento: string;
    cantidad: number;
    fechaMovimiento: string;
    motivo: string;
    idLote: number;
    idVenta: number;
}