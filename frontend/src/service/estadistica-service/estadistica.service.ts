import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface AdminStats {
  totalProductos: number;
  totalProveedores: number;
  totalClientes: number;
  totalUsuarios: number;
  stockBajo: number;
}

export interface ComprasStats {
  totalCompras: number;
  totalProveedores: number;
  montoMes: number;
}

export interface InventarioStats {
  totalProductos: number;
  totalExistencias: number;
  stockBajo: number;
  agotados: number;
}

export interface VentasStats {
  totalVentas: number;
  totalClientes: number;
  montoMes: number;
  montoHoy: number;
}

@Injectable({
  providedIn: 'root',
})
export class EstadisticaService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}estadisticas`;
  }

  adminStats(): Observable<AdminStats> {
    return this.httpClient.get<AdminStats>(`${this.urlApi}/admin`);
  }

  comprasStats(): Observable<ComprasStats> {
    return this.httpClient.get<ComprasStats>(`${this.urlApi}/compras`);
  }

  inventarioStats(): Observable<InventarioStats> {
    return this.httpClient.get<InventarioStats>(`${this.urlApi}/inventario`);
  }

  ventasStats(): Observable<VentasStats> {
    return this.httpClient.get<VentasStats>(`${this.urlApi}/ventas`);
  }
}
