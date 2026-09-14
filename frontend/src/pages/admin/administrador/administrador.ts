import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrador',
  imports: [CommonModule],
  templateUrl: './administrador.html',
  styleUrl: './administrador.css',
})
export class Administrador {
  usuario: any;

  constructor(private router: Router) {
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  cerrarSesion(): void {
    localStorage.removeItem('usuario');
    localStorage.removeItem('rol');

    this.router.navigate(['/login']);
  }
}
