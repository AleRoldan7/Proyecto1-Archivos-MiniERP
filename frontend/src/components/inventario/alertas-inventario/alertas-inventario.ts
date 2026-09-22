import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { ProductoStock } from '../../../models/reportes/producto/producto-stock';
import { ReportesService } from '../../../service/reportes-service/reportes.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth-service/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-alertas-inventario',
  imports: [CommonModule, FormsModule],
  templateUrl: './alertas-inventario.html',
  styleUrl: './alertas-inventario.css',
})
export class AlertasInventario implements OnInit {

  productos: ProductoStock[] = [];
  soloBajoMinimo = true;
  limite = 50;

  constructor(private reporteService: ReportesService, private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.reporteService.productosMenorExistencia(this.soloBajoMinimo, this.limite).subscribe({
      next: (r) => (this.productos = r),
      error: (e) => Swal.fire({
        icon: 'error',
        title: 'Error',
        text: e?.error?.message || e?.error?.mensaje || 'No se pudieron cargar las alertas',
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
