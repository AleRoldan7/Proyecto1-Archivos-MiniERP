package backend.proyecto1archivosminierp.configuracion;

import backend.proyecto1archivosminierp.usuario.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserService customUserService;
    private final JwtFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())

                /*
                 * JWT = aplicación sin sesión HTTP.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .userDetailsService(customUserService)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login").permitAll()
                        .requestMatchers("/usuarios/crear").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> {

                }).csrf(csrf -> csrf.disable()).userDetailsService(customUserService)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login").permitAll()
                        .requestMatchers("/usuarios/crear").permitAll()
                        .requestMatchers("/usuarios/lista").permitAll()


                        /*RUTAS PERMITIDAS PARA EL ADMINISTRADOR*/
    // .requestMatchers("/administracion/**").hasRole("ADMIN")

    /*RUTAS PERMITIDAS PARA EL INVENTARIO*/
//                        .requestMatchers("/inventario/**").hasRole("INVENTARIO")
//
    /*RUTAS PERMITIDAS PARA LAS COMPRAS*/
//                        .requestMatchers("/compras/**").hasRole("COMPRAS")
//
//                        /*RUTAS PERMITIDAS PARA LAS VENTAS*/
//                        .requestMatchers("/ventas/**").hasRole("VENTAS")
//
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//    */
}
