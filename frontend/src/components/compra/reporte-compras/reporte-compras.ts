import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CompraReporte } from '../../../models/reportes/compra/compra-reporte';
import { ProveedorTop } from '../../../models/reportes/compra/proveedor-top';
import { ProductoComprado } from '../../../models/reportes/compra/producto-comprado';
import { ReportesService } from '../../../service/reportes-service/reportes.service';
import { AuthService } from '../../../service/auth-service/auth.service';

@Component({
  selector: 'app-reporte-compras',
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-compras.html',
  styleUrl: './reporte-compras.css',
})
export class ReportesCompras implements OnInit {

  comprasRangoFiltro = { desde: '', hasta: '' };
  comprasRangoResultado: CompraReporte[] = [];

  topProveedoresFiltro = { desde: '', hasta: '', limite: 5 };
  topProveedoresResultado: ProveedorTop[] = [];

  masAdquiridosFiltro = { desde: '', hasta: '', limite: 10 };
  masAdquiridosResultado: ProductoComprado[] = [];

  constructor(private reporteService: ReportesService, private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    const hoy = new Date();
    const hace30 = new Date();
    hace30.setDate(hoy.getDate() - 30);

    this.comprasRangoFiltro.desde = this.formatear(hace30);
    this.comprasRangoFiltro.hasta = this.formatear(hoy);
    this.topProveedoresFiltro.desde = this.formatear(hace30);
    this.topProveedoresFiltro.hasta = this.formatear(hoy);
    this.masAdquiridosFiltro.desde = this.formatear(hace30);
    this.masAdquiridosFiltro.hasta = this.formatear(hoy);
  }

  private formatear(fecha: Date): string {
    return fecha.toISOString().slice(0, 16);
  }

  private aIso(fecha: string): string | null {
    if (!fecha) return null;
    return fecha.length === 16 ? `${fecha}:00` : fecha;
  }

  private mostrarError(mensaje: string, error: any): void {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: error?.error?.message || error?.error?.mensaje || mensaje,
    });
  }

  buscarComprasRango(): void {
    const desde = this.aIso(this.comprasRangoFiltro.desde);
    const hasta = this.aIso(this.comprasRangoFiltro.hasta);
    if (!desde || !hasta) {
      Swal.fire({ icon: 'warning', title: 'Fechas requeridas', text: 'Indica fecha inicial y final' });
      return;
    }

    this.reporteService.comprasPorRango(desde, hasta).subscribe({
      next: (r) => (this.comprasRangoResultado = r),
      error: (e) => this.mostrarError('No se pudo obtener el reporte', e),
    });
  }

  buscarTopProveedores(): void {
    this.reporteService.topProveedores({
      desde: this.aIso(this.topProveedoresFiltro.desde),
      hasta: this.aIso(this.topProveedoresFiltro.hasta),
      limite: this.topProveedoresFiltro.limite,
    }).subscribe({
      next: (r) => (this.topProveedoresResultado = r),
      error: (e) => this.mostrarError('No se pudo obtener el top de proveedores', e),
    });
  }

  buscarMasAdquiridos(): void {
    this.reporteService.productosMasAdquiridos({
      desde: this.aIso(this.masAdquiridosFiltro.desde),
      hasta: this.aIso(this.masAdquiridosFiltro.hasta),
      limite: this.masAdquiridosFiltro.limite,
    }).subscribe({
      next: (r) => (this.masAdquiridosResultado = r),
      error: (e) => this.mostrarError('No se pudo obtener el reporte', e),
    });
  }

  regresarPanel(): void {
    const rol = this.auth.rol();
    switch (rol) {
      case 'ADMIN':
        this.router.navigate(['/administracion']);
        break;
      case 'COMPRAS':
        this.router.navigate(['/comprador']);
        break;
      default:
        this.router.navigate(['/login']);
        break;
    }
  }
}
