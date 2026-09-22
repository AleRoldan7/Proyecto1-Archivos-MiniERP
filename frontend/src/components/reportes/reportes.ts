import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductoResponse } from '../../models/producto/producto-response';
import { ProductoVendido } from '../../models/reportes/producto/producto-vendido';
import { ProductoStock } from '../../models/reportes/producto/producto-stock';
import { ProductoMovimiento } from '../../models/reportes/producto/producto-movimiento';
import { MovimientoHistorial } from '../../models/reportes/producto/movimiento-historial';
import { CompraReporte } from '../../models/reportes/compra/compra-reporte';
import { ProveedorTop } from '../../models/reportes/compra/proveedor-top';
import { ProductoComprado } from '../../models/reportes/compra/producto-comprado';
import { VentaReporte } from '../../models/reportes/venta/venta-reporte';
import { ClienteTop } from '../../models/reportes/venta/cliente-top';
import { ProductoIngresado } from '../../models/reportes/venta/producto-ingresado';
import { PeriodoReporte } from '../../models/reportes/enums/periodo-reporte';
import { ResumenVenta } from '../../models/reportes/venta/resumen-venta';
import { AccionesLog } from '../../models/reportes/log/acciones-log';
import { LogResponse } from '../../models/reportes/log/log-response';
import { ReportesService } from '../../service/reportes-service/reportes.service';
import { ProductoService } from '../../service/producto-service/producto.service';
import { AuthService } from '../../service/auth-service/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

type TabReporte = 'inventario' | 'compras' | 'ventas' | 'logs';

interface DefinicionTab {
  id: TabReporte;
  etiqueta: string;
  roles: string[];
}

@Component({
  selector: 'app-reportes',
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.css',
})
export class Reportes implements OnInit {

  private readonly todasLasTabs: DefinicionTab[] = [
    { id: 'inventario', etiqueta: 'Inventario', roles: ['INVENTARIO', 'ADMIN'] },
    { id: 'compras', etiqueta: 'Compras', roles: ['COMPRAS', 'ADMIN'] },
    { id: 'ventas', etiqueta: 'Ventas', roles: ['VENTAS', 'ADMIN'] },
    { id: 'logs', etiqueta: 'Logs', roles: ['ADMIN'] },
  ];

  tabsVisibles: DefinicionTab[] = [];
  tabActiva: TabReporte = 'inventario';
  productos: ProductoResponse[] = [];

  // ---------- Inventario ----------
  masVendidosFiltro = { desde: '', hasta: '', limite: 10 };
  masVendidosResultado: ProductoVendido[] = [];

  menorExistenciaFiltro = { soloBajoMinimo: false, limite: 20 };
  menorExistenciaResultado: ProductoStock[] = [];

  masMovimientosFiltro = { desde: '', hasta: '', limite: 10 };
  masMovimientosResultado: ProductoMovimiento[] = [];

  historialFiltro: { idProducto: number | null; desde: string; hasta: string } = {
    idProducto: null,
    desde: '',
    hasta: '',
  };
  historialResultado: MovimientoHistorial[] = [];

  // ---------- Compras ----------
  comprasRangoFiltro = { desde: '', hasta: '' };
  comprasRangoResultado: CompraReporte[] = [];

  topProveedoresFiltro = { desde: '', hasta: '', limite: 5 };
  topProveedoresResultado: ProveedorTop[] = [];

  masAdquiridosFiltro = { desde: '', hasta: '', limite: 10 };
  masAdquiridosResultado: ProductoComprado[] = [];

  // ---------- Ventas ----------
  ventasRangoFiltro = { desde: '', hasta: '' };
  ventasRangoResultado: VentaReporte[] = [];

  topClientesFiltro = { desde: '', hasta: '', limite: 10 };
  topClientesResultado: ClienteTop[] = [];

  mayoresIngresosFiltro = { desde: '', hasta: '', limite: 10 };
  mayoresIngresosResultado: ProductoIngresado[] = [];

  resumenVentasFiltro: { periodo: PeriodoReporte; desde: string; hasta: string } = {
    periodo: 'MENSUAL',
    desde: '',
    hasta: '',
  };
  resumenVentasResultado: ResumenVenta[] = [];
  periodos: PeriodoReporte[] = ['DIARIO', 'SEMANAL', 'MENSUAL', 'ANUAL'];

  // ---------- Logs ----------
  logsFiltro: {
    usuario: string;
    modulo: string;
    accion: AccionesLog | '';
    desde: string;
    hasta: string;
    limite: number;
  } = {
      usuario: '',
      modulo: '',
      accion: '',
      desde: '',
      hasta: '',
      limite: 200,
    };
  logsResultado: LogResponse[] = [];
  accionesLog: AccionesLog[] = ['CREAR', 'ACTUALIZAR', 'ELIMINAR', 'CONSULTAR', 'ERROR'];

  constructor(
    private reporteService: ReportesService,
    private productoService: ProductoService,
    private router: Router,
    private auth: AuthService
  ) { }

  ngOnInit(): void {
    const rol = this.auth.rol();

    if (!rol) {
      Swal.fire({
        icon: 'error',
        title: 'Sin acceso',
        text: 'No se pudo determinar tu rol.',
      });
      this.router.navigate(['/']);
      return;
    }

    this.tabsVisibles = this.todasLasTabs.filter((tab) => tab.roles.includes(rol));

    if (this.tabsVisibles.length === 0) {
      Swal.fire({
        icon: 'error',
        title: 'Sin acceso',
        text: 'Tu rol no tiene reportes asignados',
      });
      this.router.navigate(['/']);
      return;
    }

    this.tabActiva = this.tabsVisibles[0].id;

    if (this.puedeVer('inventario')) {
      this.productoService.obtenerProductos().subscribe({
        next: (response) => {
          this.productos = response;
        },
      });
    }
  }

  puedeVer(tab: TabReporte): boolean {
    return this.tabsVisibles.some((t) => t.id === tab);
  }

  cambiarTab(tab: TabReporte): void {
    if (!this.puedeVer(tab)) {
      return;
    }
    this.tabActiva = tab;
  }

  private aIso(fecha: string): string | null {
    if (!fecha) {
      return null;
    }
    return fecha.length === 16 ? `${fecha}:00` : fecha;
  }

  private mostrarError(mensaje: string, error: any): void {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: error?.error?.message || error?.error?.mensaje || mensaje,
    });
  }

  // ---------- Inventario ----------

  buscarMasVendidos(): void {
    this.reporteService
      .topProductosMasVendidos({
        desde: this.aIso(this.masVendidosFiltro.desde),
        hasta: this.aIso(this.masVendidosFiltro.hasta),
        limite: this.masVendidosFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.masVendidosResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el reporte de mas vendidos', error),
      });
  }

  buscarMenorExistencia(): void {
    this.reporteService
      .productosMenorExistencia(this.menorExistenciaFiltro.soloBajoMinimo, this.menorExistenciaFiltro.limite)
      .subscribe({
        next: (response) => (this.menorExistenciaResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el reporte de menor existencia', error),
      });
  }

  buscarMasMovimientos(): void {
    this.reporteService
      .productosConMasMovimientos({
        desde: this.aIso(this.masMovimientosFiltro.desde),
        hasta: this.aIso(this.masMovimientosFiltro.hasta),
        limite: this.masMovimientosFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.masMovimientosResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el reporte de mas movimientos', error),
      });
  }

  buscarHistorial(): void {
    if (this.historialFiltro.idProducto == null) {
      Swal.fire({
        icon: 'warning',
        title: 'Selecciona un producto',
        text: 'Debes elegir un producto para ver su historial',
      });
      return;
    }

    this.reporteService
      .historialDeProducto(
        this.historialFiltro.idProducto,
        this.aIso(this.historialFiltro.desde),
        this.aIso(this.historialFiltro.hasta)
      )
      .subscribe({
        next: (response) => (this.historialResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el historial del producto', error),
      });
  }

  // ---------- Compras ----------

  buscarComprasRango(): void {
    const desde = this.aIso(this.comprasRangoFiltro.desde);
    const hasta = this.aIso(this.comprasRangoFiltro.hasta);

    if (!desde || !hasta) {
      Swal.fire({ icon: 'warning', title: 'Fechas requeridas', text: 'Indica fecha inicial y final' });
      return;
    }

    this.reporteService.comprasPorRango(desde, hasta).subscribe({
      next: (response) => (this.comprasRangoResultado = response),
      error: (error) => this.mostrarError('No se pudo obtener el reporte de compras', error),
    });
  }

  buscarTopProveedores(): void {
    this.reporteService
      .topProveedores({
        desde: this.aIso(this.topProveedoresFiltro.desde),
        hasta: this.aIso(this.topProveedoresFiltro.hasta),
        limite: this.topProveedoresFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.topProveedoresResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el top de proveedores', error),
      });
  }

  buscarMasAdquiridos(): void {
    this.reporteService
      .productosMasAdquiridos({
        desde: this.aIso(this.masAdquiridosFiltro.desde),
        hasta: this.aIso(this.masAdquiridosFiltro.hasta),
        limite: this.masAdquiridosFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.masAdquiridosResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el reporte de productos mas adquiridos', error),
      });
  }

  // ---------- Ventas ----------

  buscarVentasRango(): void {
    const desde = this.aIso(this.ventasRangoFiltro.desde);
    const hasta = this.aIso(this.ventasRangoFiltro.hasta);

    if (!desde || !hasta) {
      Swal.fire({ icon: 'warning', title: 'Fechas requeridas', text: 'Indica fecha inicial y final' });
      return;
    }

    this.reporteService.ventasPorRango(desde, hasta).subscribe({
      next: (response) => (this.ventasRangoResultado = response),
      error: (error) => this.mostrarError('No se pudo obtener el reporte de ventas', error),
    });
  }

  buscarTopClientes(): void {
    this.reporteService
      .topClientes({
        desde: this.aIso(this.topClientesFiltro.desde),
        hasta: this.aIso(this.topClientesFiltro.hasta),
        limite: this.topClientesFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.topClientesResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el top de clientes', error),
      });
  }

  buscarMayoresIngresos(): void {
    this.reporteService
      .topProductosPorIngreso({
        desde: this.aIso(this.mayoresIngresosFiltro.desde),
        hasta: this.aIso(this.mayoresIngresosFiltro.hasta),
        limite: this.mayoresIngresosFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.mayoresIngresosResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el reporte de mayores ingresos', error),
      });
  }

  buscarResumenVentas(): void {
    const desde = this.aIso(this.resumenVentasFiltro.desde);
    const hasta = this.aIso(this.resumenVentasFiltro.hasta);

    if (!desde || !hasta) {
      Swal.fire({ icon: 'warning', title: 'Fechas requeridas', text: 'Indica fecha inicial y final' });
      return;
    }

    this.reporteService.resumenVentas(this.resumenVentasFiltro.periodo, desde, hasta).subscribe({
      next: (response) => (this.resumenVentasResultado = response),
      error: (error) => this.mostrarError('No se pudo obtener el resumen de ventas', error),
    });
  }

  // ---------- Logs ----------

  buscarLogs(): void {
    this.reporteService
      .logs({
        usuario: this.logsFiltro.usuario || null,
        modulo: this.logsFiltro.modulo || null,
        accion: this.logsFiltro.accion || null,
        desde: this.aIso(this.logsFiltro.desde),
        hasta: this.aIso(this.logsFiltro.hasta),
        limite: this.logsFiltro.limite,
      })
      .subscribe({
        next: (response) => (this.logsResultado = response),
        error: (error) => this.mostrarError('No se pudo obtener el log de auditoria', error),
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
