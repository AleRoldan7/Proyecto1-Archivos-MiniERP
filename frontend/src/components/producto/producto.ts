import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoResponse } from '../../models/producto/producto-response';
import { CategoriaResponse } from '../../models/producto/categoria-response';
import { ProductoService } from '../../service/producto-service/producto.service';
import { CategoriaService } from '../../service/categoria-service/categoria.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';
import Swal from 'sweetalert2';
import { ProductoRequest } from '../../models/producto/producto-request';
import { ProveedorService } from '../../service/proveedor-service/proveedor.service';
import { ProveedorResponse } from '../../models/proveedor/proveedor-response';

@Component({
  selector: 'app-producto',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './producto.html',
  styleUrl: './producto.css',
})
export class Producto implements OnInit {

  productoForm: FormGroup;
  productos: ProductoResponse[] = [];
  categorias: CategoriaResponse[] = [];
  proveedoresDisponibles: ProveedorResponse[] = [];

  editado = false;
  productoId: number | null = null;

  constructor(private formBuilder: FormBuilder, private productoService: ProductoService, private categoriaService: CategoriaService,
    private proveedorService: ProveedorService, private router: Router, private auth: AuthService) {
    this.productoForm = this.formBuilder.group({
      nombreProducto: ['', [Validators.required, Validators.maxLength(150)]],
      descripcionProducto: ['', [Validators.maxLength(255)]],
      idCategoria: ['', [Validators.required]],
      stockMinimo: [0, [Validators.required, Validators.min(0)]],
      precioVenta: [0, [Validators.required, Validators.min(1)]],
      proveedores: this.formBuilder.array([], [Validators.required, Validators.minLength(1)]),
    });
  }

  ngOnInit(): void {
    this.obtenerProductos();
    this.obtenerCategorias();
    this.obtenerProveedores();
    this.agregarProveedor();
  }

  crearProducto(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();

      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos',
      });

      return;
    }

    const producto: ProductoRequest = this.productoForm.value;

    this.productoService.crearProducto(producto).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Producto Creado',
          text: 'Producto creado correctamente',
          confirmButtonText: 'Aceptar',
        });

        this.limpiarFormulario();
        this.obtenerProductos();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo crear el producto',
        });
      },
    });
  }

  obtenerProductos(): void {
    this.productoService.obtenerProductos().subscribe({
      next: (response) => {
        this.productos = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener los productos',
        });
      },
    });
  }

  obtenerCategorias(): void {
    this.categoriaService.obtenerCategorias().subscribe({
      next: (response) => {
        this.categorias = response;
      },
    });
  }

  get proveedores(): FormArray {
    return this.productoForm.get('proveedores') as FormArray;
  }

  nuevoProveedor(): FormGroup {
    return this.formBuilder.group({
      idProveedor: ['', [Validators.required]],
      precioCompra: [0, [Validators.required, Validators.min(0.01)]],
    });
  }

  agregarProveedor(): void {
    this.proveedores.push(this.nuevoProveedor());
  }

  eliminarProveedor(index: number): void {
    if (this.proveedores.length === 1) {
      Swal.fire({
        icon: 'warning',
        title: 'Acción no permitida',
        text: 'El producto debe tener al menos un proveedor',
      });
      return;
    }
    this.proveedores.removeAt(index);
  }

  obtenerProveedores(): void {
    this.proveedorService.obtenerProveedores().subscribe({
      next: (response) => {
        this.proveedoresDisponibles = response.filter(p => p.estadoProveedor);
      },
    });
  }

  editarProducto(producto: ProductoResponse): void {
    this.editado = true;
    this.productoId = producto.idProducto;

    this.proveedores.clear();

    producto.proveedores.forEach(pp => {
      this.proveedores.push(this.formBuilder.group({
        idProveedor: [pp.idProveedor, [Validators.required]],
        precioCompra: [pp.precioCompra, [Validators.required, Validators.min(0.01)]],
      }));
    });

    this.productoForm.patchValue({
      nombreProducto: producto.nombreProducto,
      descripcionProducto: producto.descripcionProducto,
      idCategoria: producto.idCategoria,
      stockMinimo: 0,
      precioVenta: producto.precioVenta,
    });
  }

  actualizarProducto(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();

      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos obligatorios',
      });

      return;
    }

    if (this.productoId == null) {
      return;
    }

    const producto: ProductoRequest = this.productoForm.value;

    this.productoService.actualizarProducto(this.productoId, producto).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Producto Actualizado',
          text: 'Producto actualizado correctamente',
        });

        this.limpiarFormulario();
        this.obtenerProductos();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo actualizar el producto',
        });
      },
    });
  }

  desactivarProducto(producto: ProductoResponse): void {
    Swal.fire({
      title: '¿Desactivar Producto?',
      text: `Se desactivara el producto ${producto.nombreProducto}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Desactivar',
      cancelButtonText: 'Cancelar',
    }).then((resultado) => {
      if (!resultado.isConfirmed) {
        return;
      }

      this.productoService.desactivarProducto(producto.idProducto).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Producto Desactivado',
            text: 'Producto desactivado correctamente',
          });

          this.obtenerProductos();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se pudo desactivar el producto',
          });
        },
      });
    });
  }

  activarProducto(producto: ProductoResponse): void {
    this.productoService.activarProducto(producto.idProducto).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Producto Activado',
          text: 'Producto activado correctamente',
        });

        this.obtenerProductos();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo activar el producto',
        });
      },
    });
  }

  cancelarEdicion(): void {
    this.limpiarFormulario();
  }

  get esAdmin(): boolean {
    return this.auth.rol() === 'ADMIN';
  }
  
  limpiarFormulario(): void {
    this.proveedores.clear();
    this.agregarProveedor();

    this.productoForm.reset({
      nombreProducto: '',
      descripcionProducto: '',
      idCategoria: '',
      stockMinimo: 0,
      precioVenta: 0,
    });

    this.editado = false;
    this.productoId = null;
    this.productoForm.markAllAsTouched();
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
      case 'INVENTARIO':
        this.router.navigate(['/inventarista']);
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
