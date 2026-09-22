package backend.proyecto1archivosminierp.reportes.service.log;

import backend.proyecto1archivosminierp.enums.AccionesLog;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditoriaLog {

    private final LogService logService;

    @Pointcut("execution(* backend.proyecto1archivosminierp..service..*Service.*(..)) "
            + "&& !within(backend.proyecto1archivosminierp.reportes*) ")
    public void servicesDeNegocio() {
    }

    @AfterReturning("servicesDeNegocio()")
    public void registrarExito(JoinPoint joinPoint) {
        String metodo = joinPoint.getSignature().getName();
        AccionesLog accion = clasificar(metodo);

        if (accion == AccionesLog.CONSULTAR) {
            return;
        }

        logService.registrar(usuarioActual(), moduloDe(joinPoint), accion, metodo,
                "Operación ejecutada con argumentos: " + Arrays.toString(joinPoint.getArgs()), true);
    }

    @AfterThrowing(pointcut = "servicesDeNegocio()", throwing = "ex")
    public void registrarError(JoinPoint joinPoint, Throwable ex) {
        logService.registrar(usuarioActual(), moduloDe(joinPoint), AccionesLog.ERROR,
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName() + ": " + ex.getMessage(), false);
    }

    private AccionesLog clasificar(String metodo) {
        String nombre = metodo.toLowerCase();
        if (nombre.startsWith("create") || nombre.startsWith("crear") || nombre.startsWith("guardar")) {
            return AccionesLog.CREAR;
        }
        if (nombre.startsWith("update") || nombre.startsWith("actualizar") || nombre.startsWith("modificar")) {
            return AccionesLog.ACTUALIZAR;
        }
        if (nombre.startsWith("delete") || nombre.startsWith("eliminar")) {
            return AccionesLog.ELIMINAR;
        }
        return AccionesLog.CONSULTAR;
    }

    private String moduloDe(JoinPoint joinPoint) {
        String paquete = joinPoint.getSignature().getDeclaringType().getPackageName();
        String[] partes = paquete.split("\\.");
        return partes.length >= 2 ? partes[partes.length - 2].toUpperCase() : "GENERAL";
    }

    private String usuarioActual() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
