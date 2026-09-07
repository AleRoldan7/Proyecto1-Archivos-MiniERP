import { Routes } from '@angular/router';
import { LoginSistema } from '../components/login/login-sistema/login-sistema';

export const routes: Routes = [

    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginSistema },

];
