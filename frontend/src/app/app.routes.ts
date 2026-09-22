import { Routes } from '@angular/router';
import { LoginSistema } from '../components/login/login-sistema/login-sistema';
import { Administrador } from '../pages/admin/administrador/administrador';
import { Compras } from '../pages/compras/compras/compras';
import { Inventario } from '../pages/inventario/inventario/inventario';
import { Ventas } from '../pages/ventas/ventas/ventas';
import { Proveedor } from '../components/proveedor/proveedor';
import { Compra } from '../components/compra/compra';
import { Categoria } from '../components/categoria/categoria';
import { Producto } from '../components/producto/producto';
import { Cliente } from '../components/cliente/cliente';
import { Venta } from '../components/venta/venta';
import { InventarioComponent } from '../components/inventario/inventario.component';
import { Reportes } from '../components/reportes/reportes';
import { Usuario } from '../components/usuario/usuario';
import { Perfil } from '../components/perfil/perfil';
import { HistorialCompras } from '../components/compra/historial-compras/historial-compras';
import { HistorialVentas } from '../components/venta/historial-ventas/historial-ventas';
import { MovimientoInventario } from '../components/inventario/movimiento-inventario/movimiento-inventario';
import { AlertasInventario } from '../components/inventario/alertas-inventario/alertas-inventario';
import { ReportesCompras } from '../components/compra/reporte-compras/reporte-compras';
import { roleGuard } from '../guard/role.guard';
import { authGuard } from '../guard/auth.guard';
import { NoAutorizado } from '../components/no-autorizado/no-autorizado';

const TODOS = ['ADMIN', 'COMPRAS', 'INVENTARIO', 'VENTAS'];

export const routes: Routes = [

    { path: '', redirectTo: 'login', pathMatch: 'full' },

    { path: 'login', component: LoginSistema },
    { path: 'no-autorizado',   component: NoAutorizado },

    {
        path: 'administracion',
        component: Administrador,
        canActivate: [roleGuard(['ADMIN'])],
    },

    {
        path: 'comprador',
        component: Compras,
        canActivate: [roleGuard(['COMPRAS'])],
    },

    {
        path: 'inventarista',
        component: Inventario,
        canActivate: [roleGuard(['INVENTARIO'])],
    },

    {
        path: 'vendedor',
        component: Ventas,
        canActivate: [roleGuard(['VENTAS'])],
    },


    {
        path: 'productos',
        component: Producto,
        canActivate: [roleGuard(TODOS)],
    },

    {
        path: 'categorias',
        component: Categoria,
        canActivate: [roleGuard(['ADMIN'])],
    },

    {
        path: 'proveedores',
        component: Proveedor,
        canActivate: [roleGuard(['ADMIN', 'COMPRAS'])],
    },

    {
        path: 'clientes',
        component: Cliente,
        canActivate: [roleGuard(['ADMIN', 'VENTAS'])],
    },

    {
        path: 'compras',
        component: Compra,
        canActivate: [roleGuard(['ADMIN', 'COMPRAS'])],
    },

    {
        path: 'ventas',
        component: Venta,
        canActivate: [roleGuard(['ADMIN', 'VENTAS'])],
    },
    
    {
        path: 'inventario',
        component: InventarioComponent,
        canActivate: [roleGuard(['ADMIN', 'INVENTARIO'])],
    },

    {
        path: 'usuarios',
        component: Usuario,
        canActivate: [roleGuard(['ADMIN'])],
    },

    {
        path: 'perfil',
        component: Perfil,
        canActivate: [authGuard],
    },

    {
        path: 'compras/nueva',
        component: Compra,
        canActivate: [roleGuard(['ADMIN', 'COMPRAS'])],
    },

    {
        path: 'compras/historial',
        component: HistorialCompras,
        canActivate: [roleGuard(['ADMIN', 'COMPRAS'])],
    },

    {
        path: 'ventas/nueva',
        component: Venta,
        canActivate: [roleGuard(['ADMIN', 'VENTAS'])],
    },

    {
        path: 'ventas/historial',
        component: HistorialVentas,
        canActivate: [roleGuard(['ADMIN', 'VENTAS'])],
    },


    {
        path: 'inventario/existencias',
        component: InventarioComponent,
        canActivate: [roleGuard(['ADMIN', 'INVENTARIO'])],
    },

    {
        path: 'inventario/movimientos',
        component: MovimientoInventario,
        canActivate: [roleGuard(['ADMIN', 'INVENTARIO'])],
    },

    {
        path: 'inventario/alertas',
        component: AlertasInventario,
        canActivate: [roleGuard(['ADMIN', 'INVENTARIO'])],
    },

    {
        path: 'reportes',
        component: Reportes,
        canActivate: [roleGuard(TODOS)],
    },

    {
        path: 'reportes/compras',
        component: ReportesCompras,
        canActivate: [roleGuard(['ADMIN', 'COMPRAS'])],
    },

    {
        path: 'reportes/ventas',
        component: Reportes,
        canActivate: [roleGuard(['ADMIN', 'VENTAS'])],
    },

    {
        path: 'reportes/inventario',
        component: Reportes,
        canActivate: [roleGuard(['ADMIN', 'INVENTARIO'])],
    },

    {
        path: 'reportes/logs',
        component: Reportes,
        canActivate: [roleGuard(['ADMIN'])],
    },


    { path: '**', redirectTo: 'login' }

];
