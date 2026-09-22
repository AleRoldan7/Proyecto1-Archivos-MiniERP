import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VentaResponse } from '../../models/venta/venta-response';
import { ClienteResponse } from '../../models/cliente/cliente-response';
import { ProductoResponse } from '../../models/producto/producto-response';
import { InventarioResponse } from '../../models/inventario/inventario-response';
import { ClienteService } from '../../service/cliente-service/cliente.service';
import { VentaService } from '../../service/venta-service/venta.service';
import { ProductoService } from '../../service/producto-service/producto.service';
import { InventarioService } from '../../service/inventario-service/inventario.service';
import { AuthService } from '../../service/auth-service/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { VentaRequest } from '../../models/venta/venta-resquest';

const IVA = 0.12;

@Component({
  selector: 'app-venta',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './venta.html',
  styleUrl: './venta.css',
})
export class Venta implements OnInit {

  ventaForm: FormGroup;
  ventas: VentaResponse[] = [];
  clientes: ClienteResponse[] = [];
  productos: ProductoResponse[] = [];
  inventarios: InventarioResponse[] = [];

  ventaExpandidaId: number | null = null;

  constructor(private formBuilder: FormBuilder, private ventaService: VentaService, private clienteService: ClienteService, private productoService: ProductoService,
    private inventarioService: InventarioService, private router: Router, private auth: AuthService) {
    this.ventaForm = this.formBuilder.group({
      idCliente: ['', [Validators.required]],
      detalles: this.formBuilder.array([], [Validators.required, Validators.minLength(1)]),
    });
  }

  ngOnInit(): void {
    this.obtenerVentas();
    this.obtenerClientes();
    this.obtenerProductos();
    this.obtenerInventarios();
    this.agregarDetalle();
  }

  get detalles(): FormArray {
    return this.ventaForm.get('detalles') as FormArray;
  }

  nuevoDetalle(): FormGroup {
    return this.formBuilder.group({
      idProducto: ['', [Validators.required]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precioUnitario: [0],
    });
  }

  agregarDetalle(): void {
    this.detalles.push(this.nuevoDetalle());
  }

  onProductoSeleccionado(index: number): void {
    const detalle = this.detalles.at(index);
    const idProducto = Number(detalle.get('idProducto')?.value);
    const producto = this.productos.find(p => p.idProducto === idProducto);

    if (producto) {
      detalle.patchValue({ precioUnitario: producto.precioVenta ?? 0 });
    }
  }

  eliminarDetalle(index: number): void {
    if (this.detalles.length === 1) {
      Swal.fire({
        icon: 'warning',
        title: 'Accion no permitida',
        text: 'La venta debe contener al menos un producto',
      });
      return;
    }
    this.detalles.removeAt(index);
  }

  stockDisponible(idProducto: number): number {
    return this.inventarios.find((inv) => inv.idProducto === Number(idProducto))?.stock ?? 0;
  }

  subtotal(index: number): number {
    const detalle = this.detalles.at(index);
    const cantidad = Number(detalle.get('cantidad')?.value) || 0;
    const precioUnitario = Number(detalle.get('precioUnitario')?.value) || 0;
    return cantidad * precioUnitario;
  }

  subtotalGeneral(): number {
    return this.detalles.controls.reduce((acumulado, _control, index) => {
      return acumulado + this.subtotal(index);
    }, 0);
  }

  iva(): number {
    return this.subtotalGeneral() * IVA;
  }

  total(): number {
    return this.subtotalGeneral() + this.iva();
  }

  nombreCliente(idCliente: number): string {
    return this.clientes.find((c) => c.idCliente === idCliente)?.nombre ?? '—';
  }

  nombreProducto(idProducto: number): string {
    return this.productos.find((p) => p.idProducto === idProducto)?.nombreProducto ?? '—';
  }

  private detallesConStockInsuficiente(): { producto: string; disponible: number; solicitado: number }[] {
    const problemas: { producto: string; disponible: number; solicitado: number }[] = [];

    this.detalles.controls.forEach((detalle) => {
      const idProducto = Number(detalle.get('idProducto')?.value);
      const cantidad = Number(detalle.get('cantidad')?.value) || 0;
      const disponible = this.stockDisponible(idProducto);

      if (cantidad > disponible) {
        problemas.push({
          producto: this.nombreProducto(idProducto),
          disponible,
          solicitado: cantidad,
        });
      }
    });

    return problemas;
  }

  registrarVenta(): void {
    if (this.ventaForm.invalid) {
      this.ventaForm.markAllAsTouched();

      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos',
      });

      return;
    }

    const problemas = this.detallesConStockInsuficiente();

    if (problemas.length > 0) {
      const detalle = problemas
        .map((p) => `${p.producto}: disponible ${p.disponible}, solicitado ${p.solicitado}`)
        .join('\n');

      Swal.fire({
        icon: 'warning',
        title: 'Stock insuficiente',
        text: `No hay existencias suficientes para:\n${detalle}`,
      });

      return;
    }

    const venta: VentaRequest = {
      idCliente: Number(this.ventaForm.value.idCliente),
      detalles: this.detalles.controls.map(d => ({
        idProducto: Number(d.get('idProducto')?.value),
        cantidad: Number(d.get('cantidad')?.value),
      })),
    };

    this.ventaService.registrarVenta(venta).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Venta Registrada',
          html: `Venta <b>#${response.idVenta}</b> registrada correctamente.<br>¿Deseas descargar la factura?`,
          showCancelButton: true,
          confirmButtonText: '📄 Descargar factura',
          cancelButtonText: 'Cerrar',
          confirmButtonColor: '#4f46e5',
        }).then((result) => {
          if (result.isConfirmed) {
            this.descargarFactura(response);
          }
        });

        this.limpiarFormulario();
        this.obtenerVentas();
        this.obtenerInventarios();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo registrar la venta',
        });
      },
    });
  }

  obtenerVentas(): void {
    this.ventaService.obtenerVentas().subscribe({
      next: (response) => {
        this.ventas = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener las ventas',
        });
      },
    });
  }

  obtenerClientes(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (response) => {
        this.clientes = response;
      },
    });
  }

  obtenerProductos(): void {
    this.productoService.disponiblesParaVenta().subscribe({
      next: (response) => {
        this.productos = response;
      },
    });
  }

  obtenerInventarios(): void {
    this.inventarioService.obtenerInventarios().subscribe({
      next: (response) => {
        this.inventarios = response;
      },
    });
  }

  alternarDetalleVenta(venta: VentaResponse): void {
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
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo descargar la factura',
        });
      },
    });
  }

  limpiarFormulario(): void {
    this.detalles.clear();
    this.agregarDetalle();

    this.ventaForm.reset({
      idCliente: '',
    });

    this.ventaForm.markAllAsTouched();
  }

  regresarPanel(): void {
    const rol = this.auth.rol();

    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
      return;
    }

    if (rol === 'VENTAS') {
      this.router.navigate(['/vendedor']);
      return;
    }

    this.router.navigate(['/']);
  }
}
