import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InventarioResponse } from '../../models/inventario/inventario-response';
import { InventarioRequest } from '../../models/inventario/inventario-request';
import { LoteInventarioResponse } from '../../models/inventario/lote-inventario-response';

@Injectable({
  providedIn: 'root',
})
export class InventarioService {

   private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}inventario`;
  }

  obtenerInventarios(): Observable<InventarioResponse[]> {
    return this.httpClient.get<InventarioResponse[]>(`${this.urlApi}/lista-inventario`);
  }
 
  obtenerInventarioPorId(idInventario: number): Observable<InventarioResponse> {
    return this.httpClient.get<InventarioResponse>(`${this.urlApi}/${idInventario}`);
  }
 
  actualizarInventario(idInventario: number, inventario: InventarioRequest): Observable<InventarioResponse> {
    return this.httpClient.put<InventarioResponse>(`${this.urlApi}/actualizar-inventario/${idInventario}`, inventario);
  }
 
  obtenerLotesPorProducto(idProducto: number): Observable<LoteInventarioResponse[]> {
    return this.httpClient.get<LoteInventarioResponse[]>(`${this.urlApi}/producto/${idProducto}/lotes`);
  }
}
