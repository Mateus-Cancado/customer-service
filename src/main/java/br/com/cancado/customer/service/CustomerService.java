package br.com.cancado.customer.service;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.dto.UpdatePasswordDTO;
import br.com.cancado.customer.enums.CustomerStatus;
import br.com.cancado.customer.exception.CustomerIsActiveException;
import br.com.cancado.customer.exception.CustomerIsInactiveException;
import br.com.cancado.customer.exception.CustomerNotFoundException;
import br.com.cancado.customer.exception.InvalidCredentialsException;
import br.com.cancado.customer.mapper.CustomerMapper;
import br.com.cancado.customer.model.Customer;
import br.com.cancado.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public CustomerResponseDTO getProfile(UUID customerId) {
        if (customerId == null) return null;

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return customerMapper.toResponse(customer);
    }

    @Transactional
    public void delete(UUID customerId) {
        if (customerId == null) throw new NullPointerException();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        if (customer.getStatus().equals(CustomerStatus.INACTIVE)) {
            throw new CustomerIsInactiveException("Falha ao deletar. Usuário já está inativo.");
        }

        customer.setStatus(CustomerStatus.INACTIVE);
        customerRepository.save(customer);
    }

    public void activeProfile(UUID customerId) {
        if (customerId == null) throw new NullPointerException();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        if (customer.getStatus().equals(CustomerStatus.ACTIVE)) {
            throw new CustomerIsActiveException("Falha ao ativar. Usuário já está ativo.");
        }

        customer.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);
    }

    public void updatePassword(UUID customerId, @Valid UpdatePasswordDTO request) {
        if (customerId == null) throw new NullPointerException();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        if (!passwordEncoder.matches(request.currentPassword(), customer.getPasswordHash())) {
            throw new InvalidCredentialsException("Senha incorreta.");
        }

        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        customer.setPasswordHash(newPasswordHash);

        customerRepository.save(customer);
    }
}
