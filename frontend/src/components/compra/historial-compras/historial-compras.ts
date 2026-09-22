import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CompraResponse } from '../../../models/compra/compra-response';
import { CompraService } from '../../../service/compra-service/compra.service';
import { ProveedorService } from '../../../service/proveedor-service/proveedor.service';
import { ProductoService } from '../../../service/producto-service/producto.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth-service/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-historial-compras',
  imports: [CommonModule, FormsModule],
  templateUrl: './historial-compras.html',
  styleUrl: './historial-compras.css',
})
export class HistorialCompras implements OnInit {

  compras: CompraResponse[] = [];
  proveedores: any[] = [];
  productos: any[] = [];
  compraExpandidaId: number | null = null;

  filtroDesde = '';
  filtroHasta = '';

  constructor(private compraService: CompraService, private proveedorService: ProveedorService, private productoService: ProductoService,
    private router: Router, private auth: AuthService) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.compraService.obtenerCompras().subscribe({
      next: (response) => (this.compras = response),
      error: () => Swal.fire({ icon: 'error', title: 'Error', text: 'No se pudieron cargar las compras' }),
    });

    this.proveedorService.obtenerProveedores().subscribe({
      next: (response) => (this.proveedores = response),
    });

    this.productoService.obtenerProductos().subscribe({
      next: (response) => (this.productos = response),
    });
  }

  nombreProveedor(idProveedor: number): string {
    return this.proveedores.find((p) => p.idProveedor === idProveedor)?.nombreProveedor ?? '—';
  }

  nombreProducto(idProducto: number): string {
    return this.productos.find((p) => p.idProducto === idProducto)?.nombreProducto ?? '—';
  }

  totalCompra(compra: CompraResponse): number {
    return compra.detalles.reduce((acc, d) => acc + d.cantidad * d.precioUnitario, 0);
  }

  alternarCompra(compra: CompraResponse): void {
    this.compraExpandidaId = this.compraExpandidaId === compra.idCompra ? null : compra.idCompra;
  }

  comprasFiltradas(): CompraResponse[] {
    if (!this.filtroDesde && !this.filtroHasta) return this.compras;

    return this.compras.filter((c) => {
      const fecha = new Date(c.fechaCompra).getTime();
      const desde = this.filtroDesde ? new Date(this.filtroDesde).getTime() : 0;
      const hasta = this.filtroHasta ? new Date(this.filtroHasta).getTime() + 86400000 : Infinity;
      return fecha >= desde && fecha <= hasta;
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
