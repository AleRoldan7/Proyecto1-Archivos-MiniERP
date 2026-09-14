import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-compras',
  imports: [CommonModule],
  templateUrl: './compras.html',
  styleUrl: './compras.css',
})
export class Compras {

  usuario: any;

  constructor(private router: Router) {

    const usuarioGuardado =
      localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
