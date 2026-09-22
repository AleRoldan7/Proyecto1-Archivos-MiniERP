import { ProductoProveedorResponse } from "./producto-proveedor-response";

export interface ProductoResponse {
    idProducto: number;
    nombreProducto: string;
    descripcionProducto: string;
    idCategoria: number;
    nombreCategoria: string;
    estadoProducto: boolean;
    precioVenta: number;
    proveedores: ProductoProveedorResponse[];
}