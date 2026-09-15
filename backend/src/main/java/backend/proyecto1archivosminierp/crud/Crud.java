package backend.proyecto1archivosminierp.crud;

import java.util.List;

public interface Crud<REQUEST, RESPONSE, ID> {

    RESPONSE create(REQUEST request);

    RESPONSE update(ID id, REQUEST request);

    RESPONSE getById(ID id);

    List<RESPONSE> getAll();

    void delete(ID id);
}
