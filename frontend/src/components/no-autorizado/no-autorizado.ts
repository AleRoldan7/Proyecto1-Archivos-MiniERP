import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service/auth.service';

@Component({
  selector: 'app-no-autorizado',
  imports: [CommonModule],
  templateUrl: './no-autorizado.html',
  styleUrl: './no-autorizado.css',
})
export class NoAutorizado {

  constructor(private router: Router,private auth: AuthService) {}

  irAlPanel(): void {
    const rol = this.auth.rol();

    switch (rol) {
      case 'ADMIN':      this.router.navigate(['/administracion']); break;
      case 'COMPRAS':    this.router.navigate(['/comprador']);      break;
      case 'INVENTARIO': this.router.navigate(['/inventarista']);   break;
      case 'VENTAS':     this.router.navigate(['/vendedor']);       break;
      default:           this.router.navigate(['/login']);          break;
    }
  }
}
