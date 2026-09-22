import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { InventarioResponse } from '../../models/inventario/inventario-response';
import { LoteInventarioResponse } from '../../models/inventario/lote-inventario-response';
import { InventarioService } from '../../service/inventario-service/inventario.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';

@Component({
  selector: 'app-inventario',
  imports: [CommonModule, FormsModule],
  templateUrl: './inventario.html',
  styleUrl: './inventario.css',
})
export class InventarioComponent implements OnInit {


  inventarios: InventarioResponse[] = [];
 
  editandoId: number | null = null;
  nuevoStockMinimo = 0;
 
  productoSeleccionado: InventarioResponse | null = null;
  lotes: LoteInventarioResponse[] = [];
  cargandoLotes = false;
 
  constructor(private inventarioService: InventarioService, private router: Router, private auth: AuthService) {}
 
  ngOnInit(): void {
    this.obtenerInventarios();
  }
 
  obtenerInventarios(): void {
    this.inventarioService.obtenerInventarios().subscribe({
      next: (response) => {
        this.inventarios = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo obtener el inventario',
        });
      },
    });
  }
 
  stockBajo(inventario: InventarioResponse): boolean {
    return inventario.stock <= inventario.stockMinimo;
  }
 
  editarStockMinimo(inventario: InventarioResponse): void {
    this.editandoId = inventario.idInventario;
    this.nuevoStockMinimo = inventario.stockMinimo;
  }
 
  cancelarEdicion(): void {
    this.editandoId = null;
  }
 
  guardarStockMinimo(inventario: InventarioResponse): void {
    if (this.nuevoStockMinimo == null || this.nuevoStockMinimo < 0) {
      Swal.fire({
        icon: 'warning',
        title: 'Valor invalido',
        text: 'El stock minimo debe ser mayor o igual a 0',
      });
      return;
    }
 
    this.inventarioService
      .actualizarInventario(inventario.idInventario, { stockMinimo: this.nuevoStockMinimo })
      .subscribe({
        next: (actualizado) => {
          inventario.stockMinimo = actualizado.stockMinimo;
 
          Swal.fire({
            icon: 'success',
            title: 'Stock minimo actualizado',
            text: 'Se actualizo correctamente',
          });
 
          this.editandoId = null;
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se pudo actualizar el stock minimo',
          });
        },
      });
  }
 
  verLotes(inventario: InventarioResponse): void {
    this.productoSeleccionado = inventario;
    this.cargandoLotes = true;
    this.lotes = [];
 
    this.inventarioService.obtenerLotesPorProducto(inventario.idProducto).subscribe({
      next: (response) => {
        this.lotes = response;
        this.cargandoLotes = false;
      },
      error: () => {
        this.cargandoLotes = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener los lotes del producto',
        });
      },
    });
  }
 
  cerrarLotes(): void {
    this.productoSeleccionado = null;
    this.lotes = [];
  }
 
  regresarPanel(): void {
    const rol = this.auth.rol();
 
    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
      return;
    }
 
    if (rol === 'INVENTARIO') {
      this.router.navigate(['/inventarista']);
      return;
    }
 
    this.router.navigate(['/']);
  }
}
