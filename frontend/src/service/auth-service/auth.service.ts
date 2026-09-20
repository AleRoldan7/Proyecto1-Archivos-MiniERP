import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RestConstants } from '../../shared/rest-api-const';
import { Router } from '@angular/router';
import { JwtPayLoad } from '../../models/login/jwt-toke';
import { LoginRequest } from '../../models/login/login-request';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../../models/login/login-response';
import { LoginService } from '../login-service/login.service';
import { jwtDecode } from 'jwt-decode';

const TOKEN_KEY = 'erp_token';
const ROLE_KEY = 'erp_role';
const USERNAME_KEY = 'erp_username';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  restConstants = new RestConstants();
  constructor(private loginService: LoginService, private router: Router) { }

  private payload = signal<JwtPayLoad | null>(this.decodedStored());
  private storedRole = signal<string | null>(localStorage.getItem(ROLE_KEY));
  private storedUser = signal<string | null>(localStorage.getItem(USERNAME_KEY));

  isAuth = computed(() => {
    const auth = this.payload();
    return !!auth && auth.exp * 1000 > Date.now();
  });

  rol = computed<string | null>(() => this.storedRole());
  userName = computed<string | null>(() => this.storedUser() ?? this.payload()?.sub ?? null);

  login(credenciales: LoginRequest): Observable<LoginResponse> {
    return this.loginService.loginUsuario(credenciales).pipe(

      tap((response) => {
        localStorage.setItem(TOKEN_KEY, response.token);
        localStorage.setItem(ROLE_KEY, response.rol);
        localStorage.setItem(USERNAME_KEY, response.username);

        this.payload.set(this.safeDecode(response.token));
        this.storedRole.set(response.rol);
        this.storedUser.set(response.username);


      })
    )
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(ROLE_KEY);
    localStorage.removeItem(USERNAME_KEY);
    this.payload.set(null); this.storedRole.set(null);
    this.storedUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  hasAnyRole(roles: string[]): boolean {
    const currentRole = this.rol();
    return !!currentRole && roles.includes(currentRole);
  }

  private decodedStored(): JwtPayLoad | null {
    const token = localStorage.getItem(TOKEN_KEY);
    return token ? this.safeDecode(token): null;
  }

  private safeDecode(token: string): JwtPayLoad | null {

    try {
      return jwtDecode<JwtPayLoad>(token);
    } catch {
      return null;
    }
  }

}
