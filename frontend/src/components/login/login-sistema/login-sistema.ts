import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginRequest } from '../../../models/login/login-request';
import { LoginResponse } from '../../../models/login/login-response';
import { AuthService } from '../../../service/auth-service/auth.service';

@Component({
  selector: 'app-login-sistema',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login-sistema.html',
  styleUrl: './login-sistema.css',
})
export class LoginSistema {

  userName: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login(): void {
    this.authService.login({ userName: this.userName, password: this.password })
      .subscribe({
        next: (resp) => {
          Swal.fire({
            icon: 'success',
            title: 'Login exitoso',
            text: `Bienvenido ${resp.username}`,
            timer: 1500,
            showConfirmButton: false
          });
          this.redireccionar(resp.rol);
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Error de autenticación',
            text: 'Credenciales incorrectas. Inténtelo de nuevo.',
          });
          console.error(err);
        }
      });
  }

  private redireccionar(rol: string): void {
    switch (rol.toUpperCase()) {
      case 'ADMIN':      this.router.navigate(['/administracion']); break;
      case 'COMPRAS':    this.router.navigate(['/comprador']); break;
      case 'INVENTARIO': this.router.navigate(['/inventarista']); break;
      case 'VENTAS':     this.router.navigate(['/vendedor']); break;
      default:
        Swal.fire({
          icon: 'error',
          title: 'Rol no reconocido',
          text: `El rol ${rol} no está configurado.`,
        });
    }
  }
}