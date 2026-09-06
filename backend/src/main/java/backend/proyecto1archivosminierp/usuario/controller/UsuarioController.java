package backend.proyecto1archivosminierp.usuario.controller;

import backend.proyecto1archivosminierp.usuario.dto.UsuarioRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioResponseDTO;
import backend.proyecto1archivosminierp.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.crearUsuario(usuarioRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

}
