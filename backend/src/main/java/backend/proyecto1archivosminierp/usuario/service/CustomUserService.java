package backend.proyecto1archivosminierp.usuario.service;


import backend.proyecto1archivosminierp.usuario.entity.UsuarioEntity;
import backend.proyecto1archivosminierp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder().username(usuario.getUserName()).password(usuario.getPassword()).authorities(new SimpleGrantedAuthority(
                "ROLE_" + usuario.getRolEntity().getNombreRol())).disabled(!usuario.isEstadoUsuario()).build();
    }
}
