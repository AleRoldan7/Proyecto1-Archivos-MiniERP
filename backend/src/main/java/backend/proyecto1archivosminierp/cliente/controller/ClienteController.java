package backend.proyecto1archivosminierp.cliente.controller;

import backend.proyecto1archivosminierp.cliente.dto.ClienteRequestDTO;
import backend.proyecto1archivosminierp.cliente.dto.ClienteResponseDTO;
import backend.proyecto1archivosminierp.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VENTAS','ADMIN')")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/crear-cliente")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(clienteRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.getById(id));
    }

    @GetMapping("/lista-clientes")
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAll());
    }

    @PutMapping("/actualizar-cliente/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.ok(clienteService.update(id, clienteRequestDTO));
    }

    @DeleteMapping("/eliminar-cliente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
