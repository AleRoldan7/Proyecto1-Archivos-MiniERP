package backend.proyecto1archivosminierp.usuario.controller;

import backend.proyecto1archivosminierp.usuario.dto.RolResponseDTO;
import backend.proyecto1archivosminierp.usuario.entity.RolEntity;
import backend.proyecto1archivosminierp.usuario.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;

    @GetMapping("/lista")
    public ResponseEntity<List<RolResponseDTO>> listar() {

        List<RolResponseDTO> roles = rolRepository.findAll().stream()
                .map(this::convertir)
                .toList();

        return ResponseEntity.ok(roles);
    }

    private RolResponseDTO convertir(RolEntity rol) {
        RolResponseDTO dto = new RolResponseDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        dto.setDescripcionRol(rol.getDescripcionRol());
        return dto;
    }
}