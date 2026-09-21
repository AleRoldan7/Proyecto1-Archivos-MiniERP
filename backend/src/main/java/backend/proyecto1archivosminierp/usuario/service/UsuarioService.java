package backend.proyecto1archivosminierp.usuario.service;

import backend.proyecto1archivosminierp.configuracion.JwtService;
import backend.proyecto1archivosminierp.excepciones.DatosNoEncontrados;
import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.usuario.dto.LoginRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.LoginResponseDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioResponseDTO;
import backend.proyecto1archivosminierp.usuario.entity.RolEntity;
import backend.proyecto1archivosminierp.usuario.entity.UsuarioEntity;
import backend.proyecto1archivosminierp.usuario.repository.RolRepository;
import backend.proyecto1archivosminierp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        UsuarioEntity usuarioEntity = usuarioRepository.findByUserName(loginRequestDTO.getUserName()).orElseThrow((
                () -> new DatosNoEncontrados("Usuario no encontrado")));

        if (!usuarioEntity.isEstadoUsuario()) {
            throw new DatosNoEncontrados("El usuario está desactivado");
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuarioEntity.getPassword())) {
            throw new DatosNoEncontrados("Usuario o contraseña incorrecta");
        }

        UserDetails userDetails = User
                .builder()
                .username(usuarioEntity.getUserName())
                .password(usuarioEntity.getPassword())
                .authorities("ROLE_" + usuarioEntity.getRolEntity().getNombreRol())
                .disabled(!usuarioEntity.isEstadoUsuario())
                .build();

        /*
         * Generamos JWT
         */
        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(usuarioEntity.getIdUsuario(), usuarioEntity.getUserName(), usuarioEntity.getRolEntity().getNombreRol(),
                "Login exitoso",
                token
        );
    }

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        if (usuarioRepository.existsByUserName(usuarioRequestDTO.getUserName())) {
            throw new RuntimeException("El username ya existe");
        }

        if (usuarioRepository.existsByCorreoUsuario(usuarioRequestDTO.getCorreoUsuario())) {
            throw new RuntimeException("El correo ya existe");
        }

        RolEntity rolEntity = rolRepository.findById(usuarioRequestDTO.getIdRol()).orElseThrow(()
                -> new RuntimeException("Rol no encontrado"));

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setNombreUsuario(usuarioRequestDTO.getNombreUsuario());
        usuarioEntity.setCorreoUsuario(usuarioRequestDTO.getCorreoUsuario());
        usuarioEntity.setTelefonoUsuario(usuarioRequestDTO.getTelefonoUsuario());
        usuarioEntity.setUserName(usuarioRequestDTO.getUserName());

        usuarioEntity.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));

        usuarioEntity.setEstadoUsuario(true);

        usuarioEntity.setRolEntity(rolEntity);

        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        responseDTO.setIdUsuario(usuarioGuardado.getIdUsuario());
        responseDTO.setNombreUsuario(usuarioGuardado.getNombreUsuario());
        responseDTO.setCorreoUsuario(usuarioGuardado.getCorreoUsuario());
        responseDTO.setTelefonoUsuario(usuarioGuardado.getTelefonoUsuario());
        responseDTO.setEstadoUsuario(usuarioGuardado.isEstadoUsuario());
        responseDTO.setUserName(usuarioGuardado.getUserName());

        responseDTO.setIdRol(usuarioGuardado.getRolEntity().getIdRol());
        responseDTO.setNombreRol(usuarioGuardado.getRolEntity().getNombreRol());

        return responseDTO;
    }

    @Transactional
    public List<UsuarioResponseDTO> listarUsuarios() {

        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

            usuarioResponseDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioResponseDTO.setNombreUsuario(usuario.getNombreUsuario());
            usuarioResponseDTO.setCorreoUsuario(usuario.getCorreoUsuario());
            usuarioResponseDTO.setTelefonoUsuario(usuario.getTelefonoUsuario());
            usuarioResponseDTO.setEstadoUsuario(usuario.isEstadoUsuario());
            usuarioResponseDTO.setUserName(usuario.getUserName());

            if (usuario.getRolEntity() != null) {
                usuarioResponseDTO.setIdRol(usuario.getRolEntity().getIdRol());
                usuarioResponseDTO.setNombreRol(usuario.getRolEntity().getNombreRol());
            }
            return usuarioResponseDTO;

        }).toList();
    }

    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new DatosNoEncontrados("Usuario no encontrado con id " + id));

        // Validar username único (si cambia)
        if (!usuario.getUserName().equals(dto.getUserName())
                && usuarioRepository.existsByUserName(dto.getUserName())) {
            throw new OperacionInvalida("El username ya existe");
        }

        // Validar correo único (si cambia)
        if (!usuario.getCorreoUsuario().equals(dto.getCorreoUsuario())
                && usuarioRepository.existsByCorreoUsuario(dto.getCorreoUsuario())) {
            throw new OperacionInvalida("El correo ya existe");
        }

        RolEntity rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new DatosNoEncontrados("Rol no encontrado"));

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreoUsuario(dto.getCorreoUsuario());
        usuario.setTelefonoUsuario(dto.getTelefonoUsuario());
        usuario.setUserName(dto.getUserName());
        usuario.setRolEntity(rol);

        // Solo cambia la contraseña si viene con valor
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        UsuarioEntity guardado = usuarioRepository.save(usuario);
        return convertirAResponse(guardado);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new DatosNoEncontrados("Usuario no encontrado con id " + id));
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioResponseDTO cambiarEstado(Long id, boolean estado) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new DatosNoEncontrados("Usuario no encontrado con id " + id));
        usuario.setEstadoUsuario(estado);
        return convertirAResponse(usuarioRepository.save(usuario));
    }

    // Método auxiliar para no repetir código
    private UsuarioResponseDTO convertirAResponse(UsuarioEntity usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setCorreoUsuario(usuario.getCorreoUsuario());
        dto.setTelefonoUsuario(usuario.getTelefonoUsuario());
        dto.setEstadoUsuario(usuario.isEstadoUsuario());
        dto.setUserName(usuario.getUserName());
        if (usuario.getRolEntity() != null) {
            dto.setIdRol(usuario.getRolEntity().getIdRol());
            dto.setNombreRol(usuario.getRolEntity().getNombreRol());
        }
        return dto;
    }
}
