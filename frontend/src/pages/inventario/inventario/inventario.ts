import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { EstadisticaService, InventarioStats } from '../../../service/estadistica-service/estadistica.service';

@Component({
  selector: 'app-inventario',
  imports: [CommonModule, RouterLink],
  templateUrl: './inventario.html',
  styleUrl: './inventario.css',
})
export class Inventario implements OnInit {
  usuario: any;

  stats: InventarioStats = {
    totalProductos: 0,
    totalExistencias: 0,
    stockBajo: 0,
    agotados: 0,
  };

  constructor(private router: Router, private estadisticaService: EstadisticaService) {
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  ngOnInit(): void {
    this.estadisticaService.inventarioStats().subscribe({
      next: (response) => {
        this.stats = response;
      },
      error: () => console.error('Error cargando estadísticas'),
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
