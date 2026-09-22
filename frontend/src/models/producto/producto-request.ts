import { ProductoProveedorRequest } from "./producto-proveedor-request";

export interface ProductoRequest {
    nombreProducto: string;
    descripcionProducto?: string;
    idCategoria: number;
    stockMinimo: number;
    precioVenta: number;
    proveedores: ProductoProveedorRequest[];
}