import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { ClienteRequest } from '../../models/cliente/cliente-request';
import { Observable } from 'rxjs';
import { ClienteResponse } from '../../models/cliente/cliente-response';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}clientes`;
  }

  crearCliente(cliente: ClienteRequest): Observable<ClienteResponse> {
    return this.httpClient.post<ClienteResponse>(`${this.urlApi}/crear-cliente`, cliente);
  }
 
  actualizarCliente(idCliente: number, cliente: ClienteRequest): Observable<ClienteResponse> {
    return this.httpClient.put<ClienteResponse>(`${this.urlApi}/actualizar-cliente/${idCliente}`, cliente);
  }
 
  obtenerClientePorId(idCliente: number): Observable<ClienteResponse> {
    return this.httpClient.get<ClienteResponse>(`${this.urlApi}/${idCliente}`);
  }
 
  obtenerClientes(): Observable<ClienteResponse[]> {
    return this.httpClient.get<ClienteResponse[]>(`${this.urlApi}/lista-clientes`);
  }
 
  eliminarCliente(idCliente: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/eliminar-cliente/${idCliente}`);
  }
}
