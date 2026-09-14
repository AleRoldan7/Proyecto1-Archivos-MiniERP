import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginUsuario } from '../../../models/login-model';
import { LoginService } from '../../../service/login-service/login.service';
import { LoginResponse } from '../../../models/login-response';

@Component({
  selector: 'app-login-sistema',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login-sistema.html',
  styleUrl: './login-sistema.css',
})
export class LoginSistema {

  userName: string = '';
  password: string = '';

  mensaje: string = '';
  loginExitoso: boolean = false;

  constructor(private usuarioService: LoginService, private router: Router) { }

  login() {
    const data: LoginUsuario = {
      userName: this.userName,
      password: this.password,
    };

    this.usuarioService.loginUsuario(data).subscribe({
      next: (resp: LoginResponse) => {
        localStorage.setItem('usuario', JSON.stringify(resp));

        this.mensaje = 'Login exitoso - Bienvenido ' + (resp.username);

        this.loginExitoso = true;

        this.redireccionar(resp.rol);
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Error de autenticación',
          text: 'Credenciales incorrectas. Por favor, inténtelo de nuevo.',
        });
        this.mensaje = 'Credenciales incorrectas';
        this.loginExitoso = false;
        console.error(err);
      },
    });
  }

  private redireccionar(rol: string): void {

    switch (rol.toUpperCase()) {

      case 'ADMIN':
        this.router.navigate(['/administracion']);
        break;

      case 'COMPRAS':
        this.router.navigate(['/compras']);
        break;

      case 'INVENTARIO':
        this.router.navigate(['/inventario']);
        break;

      case 'VENTAS':
        this.router.navigate(['/ventas']);
        break;

      default:
        Swal.fire({ icon: 'error', title: 'Rol no reconocido', text: `El rol ${rol} no está configurado en el sistema.`, });
        break;
    }
  }
}