import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { VentaRequest } from '../../models/venta/venta-resquest';
import { Observable } from 'rxjs';
import { VentaResponse } from '../../models/venta/venta-response';

@Injectable({
  providedIn: 'root',
})
export class VentaService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}ventas`;
  }

  registrarVenta(venta: VentaRequest): Observable<VentaResponse> {
    return this.httpClient.post<VentaResponse>(`${this.urlApi}/registrar-venta`, venta);
  }

  obtenerVentaPorId(idVenta: number): Observable<VentaResponse> {
    return this.httpClient.get<VentaResponse>(`${this.urlApi}/${idVenta}`);
  }

  obtenerVentas(): Observable<VentaResponse[]>  {
    return this.httpClient.get<VentaResponse[]>(`${this.urlApi}/lista-ventas`);
  }

  
}
