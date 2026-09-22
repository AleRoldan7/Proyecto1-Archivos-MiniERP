import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ComprasStats, EstadisticaService } from '../../../service/estadistica-service/estadistica.service';

@Component({
  selector: 'app-compras',
  imports: [CommonModule, RouterLink],
  templateUrl: './compras.html',
  styleUrl: './compras.css',
})
export class Compras implements OnInit {

  usuario: any;

  stats: ComprasStats = {
    totalCompras: 0,
    totalProveedores: 0,
    montoMes: 0,
  };
  constructor(private router: Router, private estadisticaService: EstadisticaService) {
    const usuarioGuardado =
      localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  ngOnInit(): void {
    this.estadisticaService.comprasStats().subscribe({
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
