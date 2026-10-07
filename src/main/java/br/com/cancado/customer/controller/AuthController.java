package br.com.cancado.customer.controller;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.dto.LoginRequestDTO;
import br.com.cancado.customer.dto.RegisterRequestDTO;
import br.com.cancado.customer.dto.TokenResponseDTO;
import br.com.cancado.customer.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        CustomerResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        TokenResponseDTO response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
