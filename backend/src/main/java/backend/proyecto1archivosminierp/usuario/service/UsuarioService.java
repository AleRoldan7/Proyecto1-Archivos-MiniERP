package backend.proyecto1archivosminierp.usuario.service;

import backend.proyecto1archivosminierp.usuario.dto.LoginRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.LoginResponseDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioRequestDTO;
import backend.proyecto1archivosminierp.usuario.dto.UsuarioResponseDTO;
import backend.proyecto1archivosminierp.usuario.entity.RolEntity;
import backend.proyecto1archivosminierp.usuario.entity.UsuarioEntity;
import backend.proyecto1archivosminierp.usuario.repository.RolRepository;
import backend.proyecto1archivosminierp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
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


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        UsuarioEntity usuarioEntity = usuarioRepository.findByUserName(loginRequestDTO.getUserName()).orElseThrow((
                () -> new RuntimeException("Usuario no encontrado")));

        if (!usuarioEntity.isEstadoUsuario()) {
            throw new RuntimeException("El usuario está desactivado");
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuarioEntity.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrecta");
        }
        return new LoginResponseDTO(usuarioEntity.getIdUsuario(), usuarioEntity.getUserName(), usuarioEntity.getRolEntity().getNombreRol(),
                "Login exitoso");


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
}
