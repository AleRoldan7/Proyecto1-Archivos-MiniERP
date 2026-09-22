import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AdminStats, EstadisticaService } from '../../../service/estadistica-service/estadistica.service';

@Component({
  selector: 'app-administrador',
  imports: [CommonModule, RouterLink],
  templateUrl: './administrador.html',
  styleUrl: './administrador.css',
})
export class Administrador implements OnInit {
  usuario: any;

  stats: AdminStats = {
    totalProductos: 0,
    totalProveedores: 0,
    totalClientes: 0,
    totalUsuarios: 0,
    stockBajo: 0,
  };

  constructor(private router: Router, private estadisticaService: EstadisticaService) {
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  ngOnInit(): void {
    this.estadisticaService.adminStats().subscribe({
      next: (response) => {
        this.stats = response;
      },
      error: () => console.error('Error cargando estadísticas'),
    });
  }

  cerrarSesion(): void {
    localStorage.removeItem('usuario');
    localStorage.removeItem('rol');

    this.router.navigate(['/login']);
  }
}
