export interface ProductoRequest {
    nombreProducto: string;
    descripcionProducto?: string;
    idCategoria: number;
    stockMinimo: number;
}