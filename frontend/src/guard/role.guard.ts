import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service/auth.service';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return () => {
    const auth = inject(AuthService);
    const router = inject(Router);

    if (!auth.isAuth()) {
      router.navigate(['/login']);
      return false;
    }

    if (!auth.hasAnyRole(allowedRoles)) {
      router.navigate(['/no-autorizado']);
      return false;
    }

    return true;
  };
};