import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProveedorResponse } from '../../models/proveedor/proveedor-response';
import { ProveedorService } from '../../service/proveedor-service/proveedor.service';
import Swal from 'sweetalert2';
import { ProveedorRequest } from '../../models/proveedor/proveedor-request';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';

@Component({
  selector: 'app-proveedor',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedor.html',
  styleUrl: './proveedor.css',
})
export class Proveedor implements OnInit {

  proveedorForm: FormGroup;
  proveedores: ProveedorResponse[] = [];

  editado = false;
  proveedorId: number | null = null;

  constructor(private formBuilder: FormBuilder, private proveedorService: ProveedorService, private router: Router, private auth: AuthService) {

    this.proveedorForm = this.formBuilder.group({
      nombreProveedor: [
        '',
        [
          Validators.required,
          Validators.maxLength(150)
        ]
      ],

      telefonoProveedor: [
        '',
        [
          Validators.required,
          Validators.maxLength(15)
        ]
      ],

      correoProveedor: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(50)
        ]
      ],

      direccionProveedor: [
        '',
        [
          Validators.required,
          Validators.maxLength(200)
        ]
      ],

      estadoProveedor: [true]
    });
  }

  ngOnInit(): void {
    this.obtenerProveedores();
  }

  crearProveedor(): void {

    if (this.proveedorForm.invalid) {
      this.proveedorForm.markAllAsTouched();

      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos'
      });

      return;
    }

    const proveedor: ProveedorRequest = this.proveedorForm.value;

    this.proveedorService.crearProveedor(proveedor).subscribe({

      next: (response) => {

        Swal.fire({
          icon: 'success',
          title: 'Proveedor Creado',
          text: 'Proveedor creado correctamente',
          confirmButtonText: 'Aceptar'
        });

        this.limpiarFormulario();
        this.obtenerProveedores();
      }
    });
  }

  obtenerProveedores(): void {

    this.proveedorService.obtenerProveedores().subscribe({

      next: (response) => {
        this.proveedores = response;
      },

      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener los proveedores'
        });
      }
    });
  }

  editarProveedor(proveedor: ProveedorResponse): void {

    this.editado = true;
    this.proveedorId = proveedor.idProveedor;

    this.proveedorForm.patchValue({
      nombreProveedor: proveedor.nombreProveedor,
      telefonoProveedor: proveedor.telefonoProveedor,
      correoProveedor: proveedor.correoProveedor,
      direccionProveedor: proveedor.direccionProveedor,
      estadoProveedor: proveedor.estadoProveedor
    });
  }

  actualizarProveedor(): void {

    if (this.proveedorForm.invalid) {
      this.proveedorForm.markAllAsTouched();

      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos obligatorios'
      });

      return;
    }

    if (this.proveedorId == null) {
      return;
    }
    const proveedor: ProveedorRequest = this.proveedorForm.value;

    this.proveedorService.actualizarProveedor(this.proveedorId, proveedor).subscribe({

      next: () => {

        Swal.fire({
          icon: 'success',
          title: 'Proveedor Actualizado',
          text: 'Proveedor actualizado correctamente',
        });

        this.limpiarFormulario();
        this.obtenerProveedores();
      },

      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo actualizar el proveedor'
        });
      }
    });
  }

  eliminarProveedor(proveedor: ProveedorResponse): void {

    Swal.fire({
      title: '¿Eliminar Proveedor?',
      text: `Se eliminara al proveedor ${proveedor.nombreProveedor}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar'
    }).then((resultado) => {

      if (!resultado.isConfirmed) {
        return;
      }
      this.proveedorService.eliminarProveedor(proveedor.idProveedor).subscribe({

        next: () => {

          Swal.fire({
            icon: 'success',
            title: 'Proveedor Eliminado',
            text: 'Proveedor se elimino correctamente',
          });

          this.obtenerProveedores();
        },

        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se pudo eliminar el proveedor'
          });
        }
      });
    });
  }

  cancelarEdicion(): void {
    this.limpiarFormulario();
  }

  limpiarFormulario(): void {
    this.proveedorForm.reset({
      nombreProveedor: '',
      telefonoProveedor: '',
      correoProveedor: '',
      direccionProveedor: '',
      estadoProveedor: true
    });

    this.editado = false;
    this.proveedorId = null;

    this.proveedorForm.markAllAsTouched();
  }

  regresarPanel(): void {

  const rol = this.auth.rol();

  if (rol === 'ADMIN') {
    this.router.navigate(['/administracion']);
    return;
  }

  if (rol === 'COMPRAS') {
    this.router.navigate(['/compras']);
    return;
  }

  this.router.navigate(['/']);
}
}
