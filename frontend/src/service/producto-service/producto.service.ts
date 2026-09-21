import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ProductoRequest } from '../../models/producto/producto-request';
import { ProductoResponse } from '../../models/producto/producto-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}productos`;
  }

  crearProducto(producto: ProductoRequest): Observable<ProductoResponse> {
    return this.httpClient.post<ProductoResponse>(`${this.urlApi}/crear-producto`, producto);
  }
 
  actualizarProducto(idProducto: number, producto: ProductoRequest): Observable<ProductoResponse> {
    return this.httpClient.put<ProductoResponse>(`${this.urlApi}/actualizar-producto/${idProducto}`, producto);
  }
 
  obtenerProductoPorId(idProducto: number): Observable<ProductoResponse> {
    return this.httpClient.get<ProductoResponse>(`${this.urlApi}/${idProducto}`);
  }
 
  /**
   * El backend ya filtra solo productos activos en este endpoint.
   */
  obtenerProductos(): Observable<ProductoResponse[]> {
    return this.httpClient.get<ProductoResponse[]>(`${this.urlApi}/lista-productos`);
  }
 
  /**
   * Busqueda con filtros opcionales: texto, idCategoria, estado.
   * Ej: buscarProductos({ texto: 'jabon', idCategoria: 2, estado: true })
   */
  buscarProductos(filtros: { texto?: string; idCategoria?: number; estado?: boolean } = {}): Observable<ProductoResponse[]> {
    let params = new HttpParams();
 
    if (filtros.texto) {
      params = params.set('texto', filtros.texto);
    }
    if (filtros.idCategoria != null) {
      params = params.set('idCategoria', filtros.idCategoria);
    }
    if (filtros.estado != null) {
      params = params.set('estado', filtros.estado);
    }
 
    return this.httpClient.get<ProductoResponse[]>(`${this.urlApi}/buscar`, { params });
  }
 
  /**
   * Baja logica del producto.
   */
  desactivarProducto(idProducto: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/desactivar-producto/${idProducto}`);
  }
 
  activarProducto(idProducto: number): Observable<ProductoResponse> {
    return this.httpClient.patch<ProductoResponse>(`${this.urlApi}/${idProducto}/activar`, {});
  }

}
