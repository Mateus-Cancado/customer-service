package br.com.cancado.customer.service;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.exception.CustomerNotFoundException;
import br.com.cancado.customer.mapper.CustomerMapper;
import br.com.cancado.customer.model.Customer;
import br.com.cancado.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public CustomerResponseDTO getProfile(UUID customerId) {
        if (customerId == null) return null;

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return customerMapper.toResponse(customer);
    }
}
