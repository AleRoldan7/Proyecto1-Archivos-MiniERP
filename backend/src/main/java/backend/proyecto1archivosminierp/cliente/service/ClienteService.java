package backend.proyecto1archivosminierp.cliente.service;

import backend.proyecto1archivosminierp.cliente.dto.ClienteRequestDTO;
import backend.proyecto1archivosminierp.cliente.dto.ClienteResponseDTO;
import backend.proyecto1archivosminierp.cliente.entity.ClienteEntity;
import backend.proyecto1archivosminierp.cliente.repository.ClienteRepository;
import backend.proyecto1archivosminierp.crud.Crud;
import backend.proyecto1archivosminierp.excepciones.DatosInvalidos;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements Crud<ClienteRequestDTO, ClienteResponseDTO, Integer> {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO) {

        if (clienteRepository.existsByNit(clienteRequestDTO.getNit())) {
            throw new DatosInvalidos("El NIT ya se registro");
        }

        if (clienteRepository.existsByDpi(clienteRequestDTO.getDpi())) {
            throw new DatosInvalidos("El DPI ya se registro");
        }

        if (clienteRepository.existsByTelefono(clienteRequestDTO.getTelefono())) {
            throw new DatosInvalidos("El Telefono ya se registro");
        }

        if (clienteRepository.existsByCorreo(clienteRequestDTO.getCorreo())) {
            throw new DatosInvalidos("El Correo ya se registro");
        }

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNombre(clienteRequestDTO.getNombre());
        clienteEntity.setNit(clienteRequestDTO.getNit());
        clienteEntity.setDpi(clienteRequestDTO.getDpi());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setCorreo(clienteRequestDTO.getCorreo());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());

        ClienteEntity savedCliente = clienteRepository.save(clienteEntity);

        return devuelveCliente(savedCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Integer idCliente, ClienteRequestDTO clienteRequestDTO) {

        ClienteEntity clienteEntity = clienteRepository.findById(idCliente).orElseThrow(()
                -> new DatosNoEncontrados("No existe el Cliente con el id " + idCliente));

        clienteEntity.setNombre(clienteRequestDTO.getNombre());
        clienteEntity.setNit(clienteRequestDTO.getNit());
        clienteEntity.setDpi(clienteRequestDTO.getDpi());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setCorreo(clienteRequestDTO.getCorreo());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());

        ClienteEntity updateCLiente = clienteRepository.save(clienteEntity);

        return devuelveCliente(updateCLiente);
    }

    @Override
    public ClienteResponseDTO getById(Integer idCliente) {

        ClienteEntity clienteEntity = clienteRepository.findById(idCliente).orElseThrow(()
                -> new DatosNoEncontrados("No existe el Cliente con el id " + idCliente));

        return devuelveCliente(clienteEntity);

    }

    @Override
    public List<ClienteResponseDTO> getAll() {

        return clienteRepository.findAll().stream().map(this::devuelveCliente).toList();
    }

    @Override
    public void delete(Integer idCliente) {

        ClienteEntity clienteEntity = clienteRepository.findById(idCliente).orElseThrow(()
                -> new DatosNoEncontrados("No existe el Cliente con el id " + idCliente));

        clienteRepository.delete(clienteEntity);

    }

    private ClienteResponseDTO devuelveCliente(ClienteEntity clienteEntity) {

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setIdCliente(clienteEntity.getIdCliente());
        clienteResponseDTO.setNombre(clienteEntity.getNombre());
        clienteResponseDTO.setNit(clienteEntity.getNit());
        clienteResponseDTO.setDpi(clienteEntity.getDpi());
        clienteResponseDTO.setTelefono(clienteEntity.getTelefono());
        clienteResponseDTO.setCorreo(clienteEntity.getCorreo());
        clienteResponseDTO.setDireccion(clienteEntity.getDireccion());

        return clienteResponseDTO;
    }
}
