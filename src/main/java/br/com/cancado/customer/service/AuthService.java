package br.com.cancado.customer.service;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.dto.LoginRequestDTO;
import br.com.cancado.customer.dto.RegisterRequestDTO;
import br.com.cancado.customer.dto.TokenResponseDTO;
import br.com.cancado.customer.enums.CustomerStatus;
import br.com.cancado.customer.exception.EmailAlreadyExistsException;
import br.com.cancado.customer.exception.InvalidCredentialsException;
import br.com.cancado.customer.mapper.CustomerMapper;
import br.com.cancado.customer.model.Customer;
import br.com.cancado.customer.repository.CustomerRepository;
import br.com.cancado.customer.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public CustomerResponseDTO register(RegisterRequestDTO request) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);

        if (customerRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        String passwordHash = passwordEncoder.encode(request.password());
        Customer customer = customerMapper.toEntity(request, passwordHash);

        try {
            Customer saved = customerRepository.save(customer);
            return customerMapper.toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }
    }

    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginRequestDTO request) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);

        Customer customer = customerRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), customer.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        if (customer.getStatus() != CustomerStatus.ACTIVE) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(customer.getId());

        return new TokenResponseDTO(token, "Bearer", jwtService.getExpirationSeconds());
    }
}
