package com.tienda_api_rest.controller.usuario;

import com.tienda_api_rest.dto.request.AuthLoginRequestDTO;
import com.tienda_api_rest.dto.request.AuthRegisterRequestDTO;
import com.tienda_api_rest.dto.response.AuthResponseDTO;
import com.tienda_api_rest.service.usuario.UsuarioDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tienda/auth")
public class AuthController {

    private final UsuarioDetailsServiceImpl usuarioDetailsService;

    public AuthController(UsuarioDetailsServiceImpl usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AuthRegisterRequestDTO authRegisterRequestDTO){
        return ResponseEntity.ok(usuarioDetailsService.register(authRegisterRequestDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest){
        return ResponseEntity.ok(usuarioDetailsService.login(userRequest));
    }
}
