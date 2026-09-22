package backend.proyecto1archivosminierp.usuario.controller;

import backend.proyecto1archivosminierp.usuario.dto.LoginRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.LoginResponseDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioResponseDTO;
import backend.proyecto1archivosminierp.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        return ResponseEntity.ok(usuarioService.login(loginRequestDTO));
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.crearUsuario(usuarioRequestDTO);
        System.out.println("siiiiiiiii");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam boolean estado) {
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, estado));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> obtenerPerfil(
            java.security.Principal principal) {
        return ResponseEntity.ok(usuarioService.obtenerPorUsername(principal.getName()));
    }

}
