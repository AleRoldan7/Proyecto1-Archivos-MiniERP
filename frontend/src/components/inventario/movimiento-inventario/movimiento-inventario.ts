import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { ProductoResponse } from '../../../models/producto/producto-response';
import { MovimientoHistorial } from '../../../models/reportes/producto/movimiento-historial';
import { ReportesService } from '../../../service/reportes-service/reportes.service';
import { ProductoService } from '../../../service/producto-service/producto.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth-service/auth.service';

@Component({
  selector: 'app-movimiento-inventario',
  imports: [CommonModule, FormsModule],
  templateUrl: './movimiento-inventario.html',
  styleUrl: './movimiento-inventario.css',
})
export class MovimientoInventario implements OnInit {

  productos: ProductoResponse[] = [];
  movimientos: MovimientoHistorial[] = [];

  filtro: { idProducto: number | null; desde: string; hasta: string } = {
    idProducto: null,
    desde: '',
    hasta: '',
  };

  constructor(private reporteService: ReportesService, private productoService: ProductoService, private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    this.productoService.obtenerProductos().subscribe({
      next: (r) => (this.productos = r),
    });
  }

  private aIso(fecha: string): string | null {
    if (!fecha) return null;
    return fecha.length === 16 ? `${fecha}:00` : fecha;
  }

  buscar(): void {
    if (this.filtro.idProducto == null) {
      Swal.fire({ icon: 'warning', title: 'Selecciona un producto', text: 'Elige un producto para ver sus movimientos' });
      return;
    }

    this.reporteService.historialDeProducto(
      this.filtro.idProducto,
      this.aIso(this.filtro.desde),
      this.aIso(this.filtro.hasta)
    ).subscribe({
      next: (r) => (this.movimientos = r),
      error: (e) => Swal.fire({
        icon: 'error',
        title: 'Error',
        text: e?.error?.message || e?.error?.mensaje || 'No se pudieron obtener los movimientos',
      }),
    });
  }

  regresarPanel(): void {
    const rol = this.auth.rol();
    switch (rol) {
      case 'ADMIN':
        this.router.navigate(['/administracion']);
        break;
      case 'INVENTARIO':
        this.router.navigate(['/inventarista']);
        break;
      default:
        this.router.navigate(['/login']);
        break;
    }
  }
}
