import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoriaResponse } from '../../models/producto/categoria-response';
import { CategoriaService } from '../../service/categoria-service/categoria.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';
import Swal from 'sweetalert2';
import { CategoriaRequest } from '../../models/producto/categoria-request';

@Component({
  selector: 'app-categoria',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categoria.html',
  styleUrl: './categoria.css',
})
export class Categoria implements OnInit {

  categoriaForm: FormGroup;
  categorias: CategoriaResponse[] = [];

  editado = false;
  categoriaId: number | null = null;

  constructor(private formBuilder: FormBuilder, private categoriaService: CategoriaService, private router: Router, private auth: AuthService) {
    this.categoriaForm = this.formBuilder.group({
      nombreCategoria: ['', [Validators.required, Validators.maxLength(100)]],
      descripcion: ['', [Validators.maxLength(255)]],
    });
  }

  ngOnInit(): void {
    this.obtenerCategorias();
  }

   crearCategoria(): void {
    if (this.categoriaForm.invalid) {
      this.categoriaForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos completos',
      });
 
      return;
    }
 
    const categoria: CategoriaRequest = this.categoriaForm.value;
 
    this.categoriaService.crearCategoria(categoria).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Categoria Creada',
          text: 'Categoria creada correctamente',
          confirmButtonText: 'Aceptar',
        });
 
        this.limpiarFormulario();
        this.obtenerCategorias();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo crear la categoria',
        });
      },
    });
  }
 
  obtenerCategorias(): void {
    this.categoriaService.obtenerCategorias().subscribe({
      next: (response) => {
        this.categorias = response;
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener las categorias',
        });
      },
    });
  }
 
  editarCategoria(categoria: CategoriaResponse): void {
    this.editado = true;
    this.categoriaId = categoria.idCategoria;
 
    this.categoriaForm.patchValue({
      nombreCategoria: categoria.nombreCategoria,
      descripcion: categoria.descripcion,
    });
  }
 
  actualizarCategoria(): void {
    if (this.categoriaForm.invalid) {
      this.categoriaForm.markAllAsTouched();
 
      Swal.fire({
        icon: 'warning',
        title: 'Datos incompletos',
        text: 'Verifica que tengas los campos obligatorios',
      });
 
      return;
    }
 
    if (this.categoriaId == null) {
      return;
    }
 
    const categoria: CategoriaRequest = this.categoriaForm.value;
 
    this.categoriaService.actualizarCategoria(this.categoriaId, categoria).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Categoria Actualizada',
          text: 'Categoria actualizada correctamente',
        });
 
        this.limpiarFormulario();
        this.obtenerCategorias();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || error.error?.mensaje || 'No se pudo actualizar la categoria',
        });
      },
    });
  }
 
  eliminarCategoria(categoria: CategoriaResponse): void {
    Swal.fire({
      title: '¿Eliminar Categoria?',
      text: `Se eliminara la categoria ${categoria.nombreCategoria}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((resultado) => {
      if (!resultado.isConfirmed) {
        return;
      }
 
      this.categoriaService.eliminarCategoria(categoria.idCategoria).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Categoria Eliminada',
            text: 'Categoria se elimino correctamente',
          });
 
          this.obtenerCategorias();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.error?.message || error.error?.mensaje || 'No se pudo eliminar la categoria',
          });
        },
      });
    });
  }
 
  cancelarEdicion(): void {
    this.limpiarFormulario();
  }
 
  limpiarFormulario(): void {
    this.categoriaForm.reset({
      nombreCategoria: '',
      descripcion: '',
    });
 
    this.editado = false;
    this.categoriaId = null;
 
    this.categoriaForm.markAllAsTouched();
  }
 
  regresarPanel(): void {
    const rol = this.auth.rol();
 
    if (rol === 'ADMIN') {
      this.router.navigate(['/administracion']);
      return;
    }
 
    this.router.navigate(['/']);
  }
}
