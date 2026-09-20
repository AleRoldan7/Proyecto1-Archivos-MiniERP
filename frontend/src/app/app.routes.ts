import { Routes } from '@angular/router';
import { LoginSistema } from '../components/login/login-sistema/login-sistema';
import { Administrador } from '../pages/admin/administrador/administrador';
import { Compras } from '../pages/compras/compras/compras';
import { Inventario } from '../pages/inventario/inventario/inventario';
import { Ventas } from '../pages/ventas/ventas/ventas';
import { Proveedor } from '../components/proveedor/proveedor';

export const routes: Routes = [

    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginSistema },

    {
        path: 'administracion',
        component: Administrador
    },


    {
        path: 'compras',
        component: Compras
    },


    {
        path: 'inventario',
        component: Inventario
    },


    {
        path: 'ventas',
        component: Ventas
    },

    {
        path: 'proveedores',
        component: Proveedor
    },

    {
        path: '**',
        redirectTo: 'login'
    }

];
