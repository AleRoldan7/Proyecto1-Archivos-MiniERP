export interface ProductoStock {
    idInventario: number;
    idProducto: number;
    nombreProducto: string;
    stock: number;
    stockMinimo: number;
    bajoMinimo?: boolean;
}