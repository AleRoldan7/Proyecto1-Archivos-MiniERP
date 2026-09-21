import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { CompraRequest } from '../../models/compra/compra-request';
import { CompraResponse } from '../../models/compra/compra-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CompraService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}compras`;
  }

  crearCompra(compra: CompraRequest): Observable<CompraResponse> {
    return this.httpClient.post<CompraResponse>(`${this.urlApi}/registrar-compra`, compra);
  }

  obtenerCompraPorId(id: number): Observable<CompraResponse> {
    return this.httpClient.get<CompraResponse>(`${this.urlApi}/${id}`);
  }

  obtenerCompras(): Observable<CompraResponse[]> {
    return this.httpClient.get<CompraResponse[]>(`${this.urlApi}/lista-compras`);
  }

  actualizarCompra(id: number, compra: CompraRequest): Observable<CompraResponse> {
    return this.httpClient.put<CompraResponse>(`${this.urlApi}/actualizar-compra/${id}`, compra);
  }

  eliminarCompra(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/eliminar-compra/${id}`);
  }
}
