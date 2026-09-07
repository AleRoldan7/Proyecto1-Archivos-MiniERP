import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginUsuario } from '../../models/login-model';
import { UsuarioResponse } from '../../models/usuario-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  restConstants = new RestConstants();

  constructor(private httpClient: HttpClient, private router: Router) { }



  loginUsuario(data: LoginUsuario): Observable<UsuarioResponse> {
    return this.httpClient.post<UsuarioResponse>(`${this.restConstants.getApiUrl()}usuario/login`, data);
  }
}
