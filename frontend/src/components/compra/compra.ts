import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CompraResponse } from '../../models/compra/compra-response';
import { ProveedorRequest } from '../../models/proveedor/proveedor-request';
import { ProductoResponse } from '../../models/producto/producto-response';
import { CompraService } from '../../service/compra-service/compra.service';
import { ProveedorService } from '../../service/proveedor-service/proveedor.service';
import { ProductoService } from '../../service/producto-service/producto.service';
import { AuthService } from '../../service/auth-service/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CompraRequest } from '../../models/compra/compra-request';
import { CommonModule } from '@angular/common';
import { ProveedorResponse } from '../../models/proveedor/proveedor-response';

@Component({
  selector: 'app-compra',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compra.html',
  styleUrl: './compra.css',
})
export class Compra implements OnInit {

  compraForm: FormGroup;
  compras: CompraResponse[] = [];
  proveedores: ProveedorResponse[] = [];
  productos: ProductoResponse[] = [];
 
  editado = false;
  compraId: number | null = null;
 
  constructor(
    private formBuilder: FormBuilder,
    private compraService: CompraService,
    private proveedorService: ProveedorService,
    private productoService: ProductoService,
    private router: Router,
    private auth: AuthService
  ) {
    this.compraForm = this.formBuilder.group({
      idProveedor: ['', [Validators.required]],
      detalles: this.formBuilder.array([], [Validators.required, Validators.minLength(1)]),
    });
  }
 
  ngOnInit(): void {
    this.obtenerCompras();
    this.obtenerProveedores();
    this.obtenerProductos();
    this.agregarDetalle();
  }
 
  get detalles(): FormArray {
    return this.compraForm.get('detalles') as FormArray;
  }
 
  nuevoDetalle(): FormGroup {
    return this.formBuilder.group({
      idProducto: ['', [Validators.required]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precioUnitario: [0, [Validators.required, Validators.min(1)]],
    });
  }
 
  agregarDetalle(): void {
    this.detalles.push(this.nuevoDetalle());
  }
 
  eliminarDetalle(index: number): void {
    if (this.detalles.length === 1) {
      Swal.fire({
        icon: 'warning',
        title: 'Accion no permitida',
        text: 'La compra debe contener al menos un producto',
      });
      return;
    }
    this.detalles.removeAt(index);
  }
 
  subtotal(index: number): number {
    const detalle = this.detalles.at(index);
    const cantidad = Number(detalle.get('cantidad')?.value) || 0;
    const precioUnitario = Number(detalle.get('precioUnitario')?.value) || 0;
    return cantidad * precioUnitario;
  }
 
  total(): number {
    return this.detalles.controls.reduce((acumulado, _control, index) => {
      return acumulado + this.subtotal(index);
    }, 0);
  }
 
  nombreProveedor(idProveedor: number): string {
    return this.proveedores.find((p) => p.idProveedor === idProveedor)?.nombreProveedor ?? '—';
  }
 
  nombreProducto(idProducto: number): string {
    return this.productos.find((p) => p.idProducto === idProducto)?.nombreProducto ?? '—';
  }
 
  totalCompra(compra: CompraResponse): number {
    return compra.detalles.reduce((acumulado, detalle) => {
      return acumulado + detalle.cantidad * detalle.precioUnitario;
    }, 0);
  }
 
  crearCompra(): void {
    if (this.compraForm.invalid) {
      this.compraForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos',
      });
 
      return;
    }
 
    const compra: CompraRequest = this.compraForm.value;
 
    this.compraService.crearCompra(compra).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Compra Registrada',
          text: 'Compra registrada correctamente',
          confirmButtonText: 'Aceptar',
        });
 
        this.limpiarFormulario();
        this.obtenerCompras();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo registrar la compra',
        });
      },
    });
  }
 
  obtenerCompras(): void {
    this.compraService.obtenerCompras().subscribe({
      next: (response) => {
        this.compras = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener las compras',
        });
      },
    });
  }
 
  obtenerProveedores(): void {
    this.proveedorService.obtenerProveedores().subscribe({
      next: (response) => {
        this.proveedores = response;
      },
    });
  }
 
  obtenerProductos(): void {
    this.productoService.obtenerProductos().subscribe({
      next: (response) => {
        this.productos = response;
      },
    });
  }
 
  editarCompra(compra: CompraResponse): void {
    this.editado = true;
    this.compraId = compra.idCompra;
 
    this.detalles.clear();
 
    compra.detalles.forEach((detalle) => {
      this.detalles.push(
        this.formBuilder.group({
          idProducto: [detalle.idProducto, [Validators.required]],
          cantidad: [detalle.cantidad, [Validators.required, Validators.min(1)]],
          precioUnitario: [detalle.precioUnitario, [Validators.required, Validators.min(1)]],
        })
      );
    });
 
    this.compraForm.patchValue({
      idProveedor: compra.idProveedor,
    });
  }
 
  actualizarCompra(): void {
    if (this.compraForm.invalid) {
      this.compraForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos obligatorios',
      });
 
      return;
    }
 
    if (this.compraId == null) {
      return;
    }
 
    const compra: CompraRequest = this.compraForm.value;
 
    this.compraService.actualizarCompra(this.compraId, compra).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Compra Actualizada',
          text: 'Compra actualizada correctamente',
        });
 
        this.limpiarFormulario();
        this.obtenerCompras();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se puede actualizar una compra que ya se realizo',
        });
      },
    });
  }
 
  eliminarCompra(compra: CompraResponse): void {
    Swal.fire({
      title: '¿Eliminar Compra?',
      text: `Se eliminara la compra #${compra.idCompra}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((resultado) => {
      if (!resultado.isConfirmed) {
        return;
      }
 
      this.compraService.eliminarCompra(compra.idCompra).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Compra Eliminada',
            text: 'Compra se elimino correctamente',
          });
 
          this.obtenerCompras();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se puede eliminar una compra que ya se realizo',
          });
        },
      });
    });
  }
 
  cancelarEdicion(): void {
    this.limpiarFormulario();
  }
 
  limpiarFormulario(): void {
    this.detalles.clear();
    this.agregarDetalle();
 
    this.compraForm.reset({
      idProveedor: '',
    });
 
    this.editado = false;
    this.compraId = null;
 
    this.compraForm.markAllAsTouched();
  }
 
  regresarPanel(): void {
    const rol = this.auth.rol();
 
    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
      return;
    }
 
    if (rol === 'COMPRAS') {
      this.router.navigate(['/comprador']);
      return;
    }
 
    this.router.navigate(['/']);
  }
}