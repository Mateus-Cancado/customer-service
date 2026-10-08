package br.com.cancado.customer.controller;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.dto.UpdatePasswordDTO;
import br.com.cancado.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/activate")
    public ResponseEntity<Void> activeProfile(@AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        customerService.activeProfile(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid UpdatePasswordDTO request) {
        UUID customerId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        customerService.updatePassword(customerId, request);
        return ResponseEntity.noContent().build();
    }
}
