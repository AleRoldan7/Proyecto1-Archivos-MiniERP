import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { VentaResponse } from '../../../models/venta/venta-response';
import { VentaService } from '../../../service/venta-service/venta.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth-service/auth.service';

@Component({
  selector: 'app-historial-ventas',
  imports: [CommonModule, FormsModule],
  templateUrl: './historial-ventas.html',
  styleUrl: './historial-ventas.css',
})
export class HistorialVentas implements OnInit {

  ventas: VentaResponse[] = [];
  ventaExpandidaId: number | null = null;

  filtroDesde = '';
  filtroHasta = '';
  filtroCliente = '';

  constructor(private ventaService: VentaService, private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    this.cargarVentas();
  }

  cargarVentas(): void {
    this.ventaService.obtenerVentas().subscribe({
      next: (r) => (this.ventas = r),
      error: () => Swal.fire({ icon: 'error', title: 'Error', text: 'No se pudieron cargar las ventas' }),
    });
  }

  ventasFiltradas(): VentaResponse[] {
    return this.ventas.filter((v) => {
      const fecha = new Date(v.fechaVenta).getTime();
      const desde = this.filtroDesde ? new Date(this.filtroDesde).getTime() : 0;
      const hasta = this.filtroHasta ? new Date(this.filtroHasta).getTime() + 86400000 : Infinity;
      return fecha >= desde && fecha <= hasta;
    });
  }

  alternarVenta(venta: VentaResponse): void {
    this.ventaExpandidaId = this.ventaExpandidaId === venta.idVenta ? null : venta.idVenta;
  }

  descargarFactura(venta: VentaResponse): void {
    this.ventaService.descargarFactura(venta.idVenta).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `factura-${venta.idVenta}.pdf`;
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => Swal.fire({ icon: 'error', title: 'Error', text: 'No se pudo descargar la factura' }),
    });
  }

  regresarPanel(): void {
    const rol = this.auth.rol();
    switch (rol) {
      case 'ADMIN':
        this.router.navigate(['/administracion']);
        break;
      case 'VENTAS':
        this.router.navigate(['/vendedor']);
        break;
      default:
        this.router.navigate(['/login']);
        break;
    }
  }
}
