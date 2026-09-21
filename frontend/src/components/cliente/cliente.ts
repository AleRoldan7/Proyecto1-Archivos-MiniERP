import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../service/cliente-service/cliente.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';
import { ClienteResponse } from '../../models/cliente/cliente-response';
import Swal from 'sweetalert2';
import { ClienteRequest } from '../../models/cliente/cliente-request';

@Component({
  selector: 'app-cliente',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cliente.html',
  styleUrl: './cliente.css',
})
export class Cliente implements OnInit {

  clienteForm: FormGroup;
  clientes: ClienteResponse[] = [];
 
  editado = false;
  clienteId: number | null = null;
 
  constructor(
    private formBuilder: FormBuilder,
    private clienteService: ClienteService,
    private router: Router,
    private auth: AuthService
  ) {
    this.clienteForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.maxLength(150)]],
      nit: ['', [Validators.required, Validators.maxLength(20)]],
      dpi: ['', [Validators.required, Validators.maxLength(20)]],
      telefono: ['', [Validators.required, Validators.maxLength(15)]],
      correo: ['', [Validators.required, Validators.email, Validators.maxLength(50)]],
      direccion: ['', [Validators.required, Validators.maxLength(200)]],
    });
  }
 
  ngOnInit(): void {
    this.obtenerClientes();
  }
 
  crearCliente(): void {
    if (this.clienteForm.invalid) {
      this.clienteForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos',
      });
 
      return;
    }
 
    const cliente: ClienteRequest = this.clienteForm.value;
 
    this.clienteService.crearCliente(cliente).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Cliente Creado',
          text: 'Cliente creado correctamente',
          confirmButtonText: 'Aceptar',
        });
 
        this.limpiarFormulario();
        this.obtenerClientes();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo crear el cliente',
        });
      },
    });
  }
 
  obtenerClientes(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (response) => {
        this.clientes = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener los clientes',
        });
      },
    });
  }
 
  editarCliente(cliente: ClienteResponse): void {
    this.editado = true;
    this.clienteId = cliente.idCliente;
 
    this.clienteForm.patchValue({
      nombre: cliente.nombre,
      nit: cliente.nit,
      dpi: cliente.dpi,
      telefono: cliente.telefono,
      correo: cliente.correo,
      direccion: cliente.direccion,
    });
  }
 
  actualizarCliente(): void {
    if (this.clienteForm.invalid) {
      this.clienteForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos obligatorios',
      });
 
      return;
    }
 
    if (this.clienteId == null) {
      return;
    }
 
    const cliente: ClienteRequest = this.clienteForm.value;
 
    this.clienteService.actualizarCliente(this.clienteId, cliente).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Cliente Actualizado',
          text: 'Cliente actualizado correctamente',
        });
 
        this.limpiarFormulario();
        this.obtenerClientes();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo actualizar el cliente',
        });
      },
    });
  }
 
  eliminarCliente(cliente: ClienteResponse): void {
    Swal.fire({
      title: '¿Eliminar Cliente?',
      text: `Se eliminara al cliente ${cliente.nombre}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((resultado) => {
      if (!resultado.isConfirmed) {
        return;
      }
 
      this.clienteService.eliminarCliente(cliente.idCliente).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Cliente Eliminado',
            text: 'Cliente se elimino correctamente',
          });
 
          this.obtenerClientes();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se pudo eliminar el cliente',
          });
        },
      });
    });
  }
 
  cancelarEdicion(): void {
    this.limpiarFormulario();
  }
 
  limpiarFormulario(): void {
    this.clienteForm.reset({
      nombre: '',
      nit: '',
      dpi: '',
      telefono: '',
      correo: '',
      direccion: '',
    });
 
    this.editado = false;
    this.clienteId = null;
 
    this.clienteForm.markAllAsTouched();
  }
 
  regresarPanel(): void {
    const rol = this.auth.rol();
 
    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
      return;
    }
 
    if (rol === 'VENTAS') {
      this.router.navigate(['/ventas']);
      return;
    }
 
    this.router.navigate(['/']);
  }
}
