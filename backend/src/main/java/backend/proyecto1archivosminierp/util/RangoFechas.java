package backend.proyecto1archivosminierp.util;

import java.time.LocalDateTime;

public class RangoFechas {

    public RangoFechas() {
    }

    public static final LocalDateTime DESDE_DEFECTO = LocalDateTime.of(1900, 1, 1, 0, 0);
    public static final LocalDateTime HASTA_DEFECTO = LocalDateTime.of(2999, 12, 31, 23, 59);

    public static LocalDateTime desdeSeguro(LocalDateTime desde) {
        return desde != null ? desde : DESDE_DEFECTO;
    }

    public static LocalDateTime hastaSeguro(LocalDateTime hasta) {
        return hasta != null ? hasta : HASTA_DEFECTO;
    }
}
