import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from '../../shared/rest-api-const';
import { UsuarioResponse } from '../../models/usuario/usuario-response';
import { UsuarioRequest } from '../../models/usuario/usuario-request';
import { RolResponse } from '../../models/usuario/rol-response';

@Injectable({ providedIn: 'root' })
export class UsuarioService {

  private restConstants = new RestConstants();
  private readonly urlApi: string;

  constructor(private httpClient: HttpClient) {
    this.urlApi = `${this.restConstants.getApiUrl()}usuarios`;
  }

  listarUsuarios(): Observable<UsuarioResponse[]> {
    return this.httpClient.get<UsuarioResponse[]>(`${this.urlApi}/lista`);
  }

  crearUsuario(usuario: UsuarioRequest): Observable<UsuarioResponse> {
    return this.httpClient.post<UsuarioResponse>(`${this.urlApi}/crear`, usuario);
  }

  actualizarUsuario(id: number, usuario: UsuarioRequest): Observable<UsuarioResponse> {
    return this.httpClient.put<UsuarioResponse>(`${this.urlApi}/${id}`, usuario);
  }

  eliminarUsuario(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.urlApi}/${id}`);
  }

  cambiarEstado(id: number, estado: boolean): Observable<UsuarioResponse> {
    return this.httpClient.patch<UsuarioResponse>(
      `${this.urlApi}/${id}/estado?estado=${estado}`, {}
    );
  }

  listarRoles(): Observable<RolResponse[]> {
    return this.httpClient.get<RolResponse[]>(
      `${this.restConstants.getApiUrl()}roles/lista`
    );
  }
}