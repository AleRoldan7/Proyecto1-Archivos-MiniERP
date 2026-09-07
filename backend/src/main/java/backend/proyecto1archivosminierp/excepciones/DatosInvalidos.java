package backend.proyecto1archivosminierp.excepciones;

public class DatosInvalidos extends RuntimeException {

    public DatosInvalidos() {
    }

    public DatosInvalidos(String message) {
        super(message);
    }
}
