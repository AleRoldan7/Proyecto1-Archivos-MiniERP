import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { EstadisticaService, VentasStats } from '../../../service/estadistica-service/estadistica.service';

@Component({
  selector: 'app-ventas',
  imports: [CommonModule, RouterLink],
  templateUrl: './ventas.html',
  styleUrl: './ventas.css',
})
export class Ventas implements OnInit {
  
  usuario: any;
  
  stats: VentasStats = {
    totalVentas: 0,
    totalClientes: 0,
    montoMes: 0,
    montoHoy: 0,
  };
  
  constructor(private router: Router, private estadisticaService: EstadisticaService) {
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      this.usuario = JSON.parse(usuarioGuardado);
    }
  }

  ngOnInit(): void {
    this.estadisticaService.ventasStats().subscribe({
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
