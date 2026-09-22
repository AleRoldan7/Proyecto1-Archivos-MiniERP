import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

import { UsuarioService } from '../../service/usuario-service/usuario.service';
import { AuthService } from '../../service/auth-service/auth.service';
import { UsuarioResponse } from '../../models/usuario/usuario-response';

@Component({
  selector: 'app-perfil',
  imports: [CommonModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css',
})
export class Perfil implements OnInit {

  usuario: UsuarioResponse | null = null;
  cargando = true;

  constructor(private usuarioService: UsuarioService, private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.usuarioService.obtenerPerfil().subscribe({
      next: (response) => {
        this.usuario = response;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo cargar el perfil del usuario',
        });
      },
    });
  }

  iniciales(): string {
    if (!this.usuario) return '?';
    const nombre = this.usuario.nombreUsuario?.trim() ?? '';
    const partes = nombre.split(' ').filter(Boolean);
    if (partes.length === 0) return '?';
    if (partes.length === 1) return partes[0].charAt(0).toUpperCase();
    return (partes[0].charAt(0) + partes[1].charAt(0)).toUpperCase();
  }

  colorRol(): string {
    switch (this.usuario?.nombreRol?.toUpperCase()) {
      case 'ADMIN':      return 'bg-teal-600';
      case 'COMPRAS':    return 'bg-blue-600';
      case 'INVENTARIO': return 'bg-amber-500';
      case 'VENTAS':     return 'bg-emerald-600';
      default:           return 'bg-slate-600';
    }
  }

  regresar(): void {
    const rol = this.auth.rol();
    switch (rol) {
      case 'ADMIN':      this.router.navigate(['/administracion']); break;
      case 'COMPRAS':    this.router.navigate(['/comprador']); break;
      case 'INVENTARIO': this.router.navigate(['/inventarista']); break;
      case 'VENTAS':     this.router.navigate(['/vendedor']); break;
      default:           this.router.navigate(['/']); break;
    }
  }

  cerrarSesion(): void {
    Swal.fire({
      title: '¿Cerrar sesión?',
      text: 'Se cerrará tu sesión actual',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, cerrar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc2626',
    }).then((result) => {
      if (result.isConfirmed) {
        this.auth.logout();
      }
    });
  }
}