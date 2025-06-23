package com.tienda_api_rest.service.usuario;

import com.tienda_api_rest.dto.request.AuthLoginRequestDTO;
import com.tienda_api_rest.dto.request.AuthRegisterRequestDTO;
import com.tienda_api_rest.dto.response.AuthResponseDTO;
import com.tienda_api_rest.model.Usuario;
import com.tienda_api_rest.repository.usuario.UsuarioRepository;
import com.tienda_api_rest.security.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("usuario no encontrado con email: " + username)
        );
    }

    // Metodo para logear un usuario
    public AuthResponseDTO login(AuthLoginRequestDTO authLoginRequestDTO){
        String email = authLoginRequestDTO.email();
        String password = authLoginRequestDTO.password();

        Authentication authentication = authenticated(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenDeAcceso = jwtUtil.generateToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(email,"Usuario logeado exitosamente!", tokenDeAcceso, true);

        return authResponseDTO;
    }
    // Metodo para autenticar al usuario
    private Authentication authenticated(String email, String password){
        UserDetails userDetails = loadUserByUsername(email);

        if (userDetails == null) throw new BadCredentialsException("Email o contraseña invalido/s");
        if (!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Contraseña incorrecta");

        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(),userDetails.getAuthorities());
    }

    // Metodo para registrar un usuario
    public AuthResponseDTO register(AuthRegisterRequestDTO authRegisterRequestDTO){
        String email = authRegisterRequestDTO.email();

        if (usuarioRepository.findByEmail(email).isPresent()) throw new IllegalArgumentException("El usuario ya existe!");

        Usuario usuario = Usuario.builder()
                .nombre(authRegisterRequestDTO.nombre())
                .email(email)
                .password(passwordEncoder.encode(authRegisterRequestDTO.password()))
                .role(authRegisterRequestDTO.role())
                .build();

        usuarioRepository.save(usuario);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, usuario.getPassword());
        String token = jwtUtil.generateToken(authentication);

        return new AuthResponseDTO(
                email,
                "Usuario registrado correctamente",
                token,
                true
        );
    }
}
