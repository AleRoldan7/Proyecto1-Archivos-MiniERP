import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginUsuario } from '../../../models/login-model';
import { LoginService } from '../../../service/login-service/login.service';

@Component({
  selector: 'app-login-sistema',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login-sistema.html',
  styleUrl: './login-sistema.css',
})
export class LoginSistema {

  userName : string = '';
  password : string = '';

  mensaje: string = '';
  loginExitoso: boolean = false;

  constructor(private usuarioService: LoginService, private router: Router) { }

  login() {
    const data: LoginUsuario = {
      userName: this.userName,
      password: this.password,
    };

    this.usuarioService.loginUsuario(data).subscribe({
      next: (resp) => {

        localStorage.setItem('usuario', JSON.stringify(resp));

        
        this.mensaje =
              'Login exitoso - Bienvenido ' + (resp.userName || resp.nombreUsuario);

            this.loginExitoso = true;

            console.log('Usuario logueado:', resp);
           

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
}