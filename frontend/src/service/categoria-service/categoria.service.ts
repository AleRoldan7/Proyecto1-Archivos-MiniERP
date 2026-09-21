import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { CategoriaRequest } from '../../models/producto/categoria-request';
import { Observable } from 'rxjs';
import { CategoriaResponse } from '../../models/producto/categoria-response';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}categorias`;
  }

  crearCategoria(categoria: CategoriaRequest): Observable<CategoriaResponse> {
    return this.httpClient.post<CategoriaResponse>(`${this.urlApi}/crear-categoria`, categoria);
  }
 
  actualizarCategoria(idCategoria: number, categoria: CategoriaRequest): Observable<CategoriaResponse> {
    return this.httpClient.put<CategoriaResponse>(`${this.urlApi}/actualizar-categoria/${idCategoria}`, categoria);
  }
 
  obtenerCategoriaPorId(idCategoria: number): Observable<CategoriaResponse> {
    return this.httpClient.get<CategoriaResponse>(`${this.urlApi}/${idCategoria}`);
  }
 
  obtenerCategorias(): Observable<CategoriaResponse[]> {
    return this.httpClient.get<CategoriaResponse[]>(`${this.urlApi}/lista-categorias`);
  }
 
  eliminarCategoria(idCategoria: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/eliminar-categoria/${idCategoria}`);
  }

}
