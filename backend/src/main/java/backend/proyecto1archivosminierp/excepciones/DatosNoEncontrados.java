package backend.proyecto1archivosminierp.excepciones;

public class DatosNoEncontrados extends RuntimeException {

    public DatosNoEncontrados() {
    }

    public DatosNoEncontrados(String mensaje) {
        super(mensaje);
    }
}
