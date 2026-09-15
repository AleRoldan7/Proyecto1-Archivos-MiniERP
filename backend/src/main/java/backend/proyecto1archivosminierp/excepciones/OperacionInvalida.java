package backend.proyecto1archivosminierp.excepciones;

public class OperacionInvalida extends RuntimeException {

    public OperacionInvalida() {
    }

    public OperacionInvalida(String message) {
        super(message);
    }
}
