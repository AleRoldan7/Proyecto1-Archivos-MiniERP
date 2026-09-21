import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { UsuarioResponse } from '../../models/usuario/usuario-response';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RolResponse } from '../../models/usuario/rol-response';
import { UsuarioService } from '../../service/usuario-service/usuario.service';
import { AuthService } from '../../service/auth-service/auth.service';
import { Router } from '@angular/router';
import { UsuarioRequest } from '../../models/usuario/usuario-request';

@Component({
  selector: 'app-usuario',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './usuario.html',
  styleUrl: './usuario.css',
})
export class Usuario implements OnInit {

  usuarios: UsuarioResponse[] = [];
  roles: RolResponse[] = [];

  usuarioForm: FormGroup;
  editado = false;
  usuarioId: number | null = null;
  mostrarFormulario = false;

  constructor(
    private formBuilder: FormBuilder,
    private usuarioService: UsuarioService,
    private auth: AuthService,
    private router: Router
  ) {
    this.usuarioForm = this.formBuilder.group({
      nombreUsuario: ['', [Validators.required, Validators.maxLength(100)]],
      correoUsuario: ['', [Validators.required, Validators.email]],
      telefonoUsuario: ['', [Validators.required, Validators.maxLength(15)]],
      idRol: [null, [Validators.required]],
      userName: ['', [Validators.required, Validators.maxLength(50)]],
      password: ['', [Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {
    this.obtenerUsuarios();
    this.obtenerRoles();
  }

  obtenerUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (response) => (this.usuarios = response),
      error: () => this.alerta('error', 'Error', 'No se pudieron cargar los usuarios'),
    });
  }

  obtenerRoles(): void {
    this.usuarioService.listarRoles().subscribe({
      next: (response) => (this.roles = response),
      error: () => this.alerta('error', 'Error', 'No se pudieron cargar los roles'),
    });
  }

  abrirFormularioNuevo(): void {
    this.editado = false;
    this.usuarioId = null;
    this.mostrarFormulario = true;

    this.usuarioForm.reset({
      nombreUsuario: '',
      correoUsuario: '',
      telefonoUsuario: '',
      idRol: null,
      userName: '',
      password: '',
    });

    this.usuarioForm.get('password')?.setValidators([
      Validators.required,
      Validators.minLength(6),
    ]);
    this.usuarioForm.get('password')?.updateValueAndValidity();
  }

  abrirFormularioEditar(u: UsuarioResponse): void {
    this.editado = true;
    this.usuarioId = u.idUsuario;
    this.mostrarFormulario = true;

    this.usuarioForm.patchValue({
      nombreUsuario: u.nombreUsuario,
      correoUsuario: u.correoUsuario,
      telefonoUsuario: u.telefonoUsuario,
      idRol: u.idRol,
      userName: u.userName,
      password: '',
    });

    // En edición la password es opcional
    this.usuarioForm.get('password')?.setValidators([Validators.minLength(6)]);
    this.usuarioForm.get('password')?.updateValueAndValidity();
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.usuarioForm.reset();
  }

  guardar(): void {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      this.alerta('warning', 'Datos incompletos', 'Revisa los campos del formulario');
      return;
    }

    const datos: UsuarioRequest = this.usuarioForm.value;

    if (this.editado && this.usuarioId != null) {
      this.usuarioService.actualizarUsuario(this.usuarioId, datos).subscribe({
        next: () => {
          this.alerta('success', 'Actualizado', 'Usuario actualizado correctamente');
          this.mostrarFormulario = false;
          this.obtenerUsuarios();
        },
        error: (err) => this.alerta('error', 'Error',
          err.error?.message || err.error?.mensaje || 'No se pudo actualizar'),
      });
    } else {
      this.usuarioService.crearUsuario(datos).subscribe({
        next: () => {
          this.alerta('success', 'Creado', 'Usuario creado correctamente');
          this.mostrarFormulario = false;
          this.obtenerUsuarios();
        },
        error: (err) => this.alerta('error', 'Error',
          err.error?.message || err.error?.mensaje || 'No se pudo crear'),
      });
    }
  }

  eliminar(u: UsuarioResponse): void {
    Swal.fire({
      title: '¿Eliminar usuario?',
      text: `Se eliminará a ${u.nombreUsuario}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc2626',
    }).then((result) => {
      if (!result.isConfirmed) return;

      this.usuarioService.eliminarUsuario(u.idUsuario).subscribe({
        next: () => {
          this.alerta('success', 'Eliminado', 'Usuario eliminado correctamente');
          this.obtenerUsuarios();
        },
        error: (err) => this.alerta('error', 'Error',
          err.error?.message || err.error?.mensaje || 'No se pudo eliminar'),
      });
    });
  }

  cambiarEstado(u: UsuarioResponse): void {
    const nuevoEstado = !u.estadoUsuario;
    this.usuarioService.cambiarEstado(u.idUsuario, nuevoEstado).subscribe({
      next: () => {
        this.alerta('success', 'Actualizado',
          `Usuario ${nuevoEstado ? 'activado' : 'desactivado'}`);
        this.obtenerUsuarios();
      },
      error: () => this.alerta('error', 'Error', 'No se pudo cambiar el estado'),
    });
  }

  regresarPanel(): void {
    const rol = this.auth.rol();
    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
    } else {
      this.router.navigate(['/']);
    }
  }

  private alerta(icon: any, title: string, text: string): void {
    Swal.fire({ icon, title, text, confirmButtonText: 'Aceptar' });
  }
}
