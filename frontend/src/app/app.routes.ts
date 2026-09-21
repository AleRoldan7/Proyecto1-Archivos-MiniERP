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

export const routes: Routes = [

    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginSistema },

    {
        path: 'administracion',
        component: Administrador
    },

    {
        path: 'comprador',
        component: Compras
    },


    {
        path: 'inventarista',
        component: Inventario
    },


    {
        path: 'vendedor',
        component: Ventas
    },

    {
        path: 'proveedores',
        component: Proveedor
    },

    {
        path: 'compras',
        component: Compra
    },

    {
        path: 'productos',
        component: Producto
    },

    {
        path: 'categorias',
        component: Categoria
    },

    {
        path: 'clientes',
        component: Cliente
    },

    {
        path: 'ventas',
        component: Venta
    },

    {
        path: 'inventario',
        component: InventarioComponent
    },

    { 
        path: 'usuarios', 
        component: Usuario 
    },

    {
        path: 'reportes',
        component: Reportes
    },

    {
        path: '**',
        redirectTo: 'login'
    }

];
