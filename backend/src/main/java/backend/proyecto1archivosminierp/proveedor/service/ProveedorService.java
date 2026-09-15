package backend.proyecto1archivosminierp.proveedor.service;

import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.proveedor.dto.ProveedorRequestDTO;
import backend.proyecto1archivosminierp.proveedor.dto.ProveedorResponseDTO;
import backend.proyecto1archivosminierp.proveedor.entity.ProveedorEntity;
import backend.proyecto1archivosminierp.proveedor.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService implements Crud<ProveedorRequestDTO, ProveedorResponseDTO, Integer> {

    private final ProveedorRepository proveedorRepository;


    @Override
    public ProveedorResponseDTO create(ProveedorRequestDTO proveedorRequestDTO) {

        if (proveedorRepository.existsByNombreProveedor(proveedorRequestDTO.getNombreProveedor())) {
            throw new DatosInvalidos("Ya existe un proveedor con este nombre");
        }

        if (proveedorRepository.existsByCorreoProveedor(proveedorRequestDTO.getCorreoProveedor())) {
            throw new DatosInvalidos("Ya existe un proveedor con este correo");
        }

        if (proveedorRepository.existsByTelefonoProveedor(proveedorRequestDTO.getTelefonoProveedor())) {
            throw new DatosInvalidos("Ya existe un proveedor con este telefono");
        }

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setNombreProveedor(proveedorRequestDTO.getNombreProveedor());
        proveedorEntity.setTelefonoProveedor(proveedorRequestDTO.getTelefonoProveedor());
        proveedorEntity.setCorreoProveedor(proveedorRequestDTO.getCorreoProveedor());
        proveedorEntity.setDireccionProveedor(proveedorRequestDTO.getDireccionProveedor());

        if (proveedorRequestDTO.getEstadoProveedor() == null) {
            proveedorEntity.setEstadoProveedor(true);
        } else {
            proveedorEntity.setEstadoProveedor(proveedorRequestDTO.getEstadoProveedor());
        }

        ProveedorEntity savedProveedor = proveedorRepository.save(proveedorEntity);

        return devuelveProveedor(savedProveedor);
    }

    @Override
    public ProveedorResponseDTO update(Integer integer, ProveedorRequestDTO proveedorRequestDTO) {

        ProveedorEntity proveedorEntity = proveedorRepository.findById(integer).orElseThrow(()
                -> new RuntimeException("No existe el proveedor con el id: " + integer));


        proveedorEntity.setNombreProveedor(proveedorRequestDTO.getNombreProveedor());
        proveedorEntity.setTelefonoProveedor(proveedorRequestDTO.getTelefonoProveedor());
        proveedorEntity.setCorreoProveedor(proveedorRequestDTO.getCorreoProveedor());
        proveedorEntity.setDireccionProveedor(proveedorRequestDTO.getDireccionProveedor());

        if (proveedorRequestDTO.getEstadoProveedor() != null) {
            proveedorEntity.setEstadoProveedor(proveedorRequestDTO.getEstadoProveedor());
        }

        ProveedorEntity updateProveedor = proveedorRepository.save(proveedorEntity);

        return devuelveProveedor(updateProveedor);
    }

    @Override
    public ProveedorResponseDTO getById(Integer integer) {

        ProveedorEntity proveedorEntity = proveedorRepository.findById(integer).orElseThrow(()
                -> new RuntimeException("No existe el proveedor con el id: " + integer));

        return devuelveProveedor(proveedorEntity);
    }

    @Override
    public List<ProveedorResponseDTO> getAll() {
        return proveedorRepository.findAll().stream().map(this::devuelveProveedor).toList();
    }

    @Override
    public void delete(Integer integer) {

        ProveedorEntity proveedorEntity = proveedorRepository.findById(integer).orElseThrow(()
                -> new RuntimeException("No existe el proveedor con el id: " + integer));

        proveedorRepository.delete(proveedorEntity);
    }

    private ProveedorResponseDTO devuelveProveedor(ProveedorEntity proveedorEntity) {

        ProveedorResponseDTO proveedorResponseDTO = new ProveedorResponseDTO();

        proveedorResponseDTO.setIdProveedor(proveedorEntity.getIdProveedor());
        proveedorResponseDTO.setNombreProveedor(proveedorEntity.getNombreProveedor());
        proveedorResponseDTO.setTelefonoProveedor(proveedorEntity.getTelefonoProveedor());
        proveedorResponseDTO.setCorreoProveedor(proveedorEntity.getCorreoProveedor());
        proveedorResponseDTO.setDireccionProveedor(proveedorEntity.getDireccionProveedor());
        proveedorResponseDTO.setEstadoProveedor(proveedorEntity.getEstadoProveedor());

        return proveedorResponseDTO;
    }
}
