import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ProductoVendido } from '../../models/reportes/producto/producto-vendido';
import { Observable } from 'rxjs';
import { AccionesLog } from '../../models/reportes/log/acciones-log';
import { LogResponse } from '../../models/reportes/log/log-response';
import { ProductoStock } from '../../models/reportes/producto/producto-stock';
import { ProductoMovimiento } from '../../models/reportes/producto/producto-movimiento';
import { MovimientoHistorial } from '../../models/reportes/producto/movimiento-historial';
import { CompraReporte } from '../../models/reportes/compra/compra-reporte';
import { ProveedorTop } from '../../models/reportes/compra/proveedor-top';
import { ProductoComprado } from '../../models/reportes/compra/producto-comprado';
import { VentaReporte } from '../../models/reportes/venta/venta-reporte';
import { ClienteTop } from '../../models/reportes/venta/cliente-top';
import { ProductoIngresado } from '../../models/reportes/venta/producto-ingresado';
import { ResumenVenta } from '../../models/reportes/venta/resumen-venta';
import { PeriodoReporte } from '../../models/reportes/enums/periodo-reporte';
import { RangoFechas } from '../../models/reportes/enums/rango-fecha';

@Injectable({
  providedIn: 'root',
})
export class ReportesService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}reportes`;
  }

  private construirParams(
    filtros: object
  ): HttpParams {
    let params = new HttpParams();

    Object.entries(filtros).forEach(([clave, valor]) => {
      if (valor !== null && valor !== undefined && valor !== '') {
        params = params.set(clave, String(valor));
      }
    });

    return params;
  }

  // ---------- Productos e inventario ----------

  topProductosMasVendidos(filtros: RangoFechas = {}): Observable<ProductoVendido[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ProductoVendido[]>(`${this.urlApi}/mas-vendidos`, { params });
  }

  productosMenorExistencia(soloBajoMinimo: boolean, limite?: number | null): Observable<ProductoStock[]> {
    const params = this.construirParams({ soloBajoMinimo, limite });
    return this.httpClient.get<ProductoStock[]>(`${this.urlApi}/productos/menor-existencia`, { params });
  }

  productosConMasMovimientos(filtros: RangoFechas = {}): Observable<ProductoMovimiento[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ProductoMovimiento[]>(`${this.urlApi}/productos/mas-movimientos`, { params });
  }

  historialDeProducto(idProducto: number, desde?: string | null, hasta?: string | null): Observable<MovimientoHistorial[]> {
    const params = this.construirParams({ desde, hasta });
    return this.httpClient.get<MovimientoHistorial[]>(`${this.urlApi}/productos/${idProducto}/movimientos`, { params });
  }

  // ---------- Compras y proveedores ----------

  comprasPorRango(desde: string, hasta: string): Observable<CompraReporte[]> {
    const params = this.construirParams({ desde, hasta });
    return this.httpClient.get<CompraReporte[]>(`${this.urlApi}/compras`, { params });
  }

  topProveedores(filtros: RangoFechas = {}): Observable<ProveedorTop[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ProveedorTop[]>(`${this.urlApi}/proveedores/top`, { params });
  }

  productosMasAdquiridos(filtros: RangoFechas = {}): Observable<ProductoComprado[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ProductoComprado[]>(`${this.urlApi}/productos/mas-adquiridos`, { params });
  }

  // ---------- Ventas y clientes ----------

  ventasPorRango(desde: string, hasta: string): Observable<VentaReporte[]> {
    const params = this.construirParams({ desde, hasta });
    return this.httpClient.get<VentaReporte[]>(`${this.urlApi}/ventas`, { params });
  }

  topClientes(filtros: RangoFechas = {}): Observable<ClienteTop[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ClienteTop[]>(`${this.urlApi}/clientes/top`, { params });
  }

  topProductosPorIngreso(filtros: RangoFechas = {}): Observable<ProductoIngresado[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<ProductoIngresado[]>(`${this.urlApi}/productos/mayores-ingresos`, { params });
  }

  resumenVentas(periodo: PeriodoReporte, desde: string, hasta: string): Observable<ResumenVenta[]> {
    const params = this.construirParams({ periodo, desde, hasta });
    return this.httpClient.get<ResumenVenta[]>(`${this.urlApi}/ventas/resumen`, { params });
  }

  // ---------- Logs ----------

  logs(filtros: {
    usuario?: string | null;
    modulo?: string | null;
    accion?: AccionesLog | null;
    desde?: string | null;
    hasta?: string | null;
    limite?: number | null;
  } = {}): Observable<LogResponse[]> {
    const params = this.construirParams(filtros);
    return this.httpClient.get<LogResponse[]>(`${this.urlApi}/logs`, { params });
  }
}
