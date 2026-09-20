import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api-const';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginRequest } from '../../models/login/login-request';
import { UsuarioResponse } from '../../models/usuario/usuario-response';
import { Observable } from 'rxjs';
import { LoginResponse } from '../../models/login/login-response';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  restConstants = new RestConstants();

  constructor(private httpClient: HttpClient, private router: Router) { }



  loginUsuario(data: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${this.restConstants.getApiUrl()}usuarios/login`, data);
  }
}
