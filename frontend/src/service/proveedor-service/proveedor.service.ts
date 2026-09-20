import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { ProveedorRequest } from '../../models/proveedor/proveedor-request';
import { Observable } from 'rxjs';
import { ProveedorResponse } from '../../models/proveedor/proveedor-response';

@Injectable({
  providedIn: 'root',
})
export class ProveedorService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}proveedores`;
  }

  crearProveedor(proveedor: ProveedorRequest): Observable<ProveedorResponse> {
    return this.httpClient.post<ProveedorResponse>(`${this.urlApi}/crear-proveedor`, proveedor);
  }

  obtenerProveedores(): Observable<ProveedorResponse[]> {
    return this.httpClient.get<ProveedorResponse[]>(`${this.urlApi}/lista-proveedores`);
  }

  obtenerProveedorId(idProveedor: number): Observable<ProveedorResponse> {
    return this.httpClient.get<ProveedorResponse>(`${this.urlApi}/${idProveedor}`);
  }

   actualizarProveedor(idProveedor: number, proveedorUpdate: ProveedorRequest): Observable<ProveedorResponse> {
    return this.httpClient.put<ProveedorResponse>(`${this.urlApi}/actualizar-proveedor/${idProveedor}`, proveedorUpdate);
  }

  eliminarProveedor(idProveedor: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/eliminar/${idProveedor}`);
  }

}
