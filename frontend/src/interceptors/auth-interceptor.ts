import { HttpInterceptorFn } from "@angular/common/http";

export const AuthInterceptor: HttpInterceptorFn = (request, next) => {
    const token = localStorage.getItem('erp_token');

     if (token && !request.url.includes('/usuarios/login')) {
    const cloned = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  return next(request);
};