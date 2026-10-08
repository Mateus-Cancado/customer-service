package br.com.cancado.customer.controller;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDTO> getMe(@AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        CustomerResponseDTO response = customerService.getProfile(customerId);
        return ResponseEntity.ok(response);
    }
}
