package br.com.cancado.customer.mapper;

import br.com.cancado.customer.dto.CustomerResponseDTO;
import br.com.cancado.customer.dto.RegisterRequestDTO;
import br.com.cancado.customer.enums.CustomerStatus;
import br.com.cancado.customer.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomerMapper {

    public Customer toEntity(RegisterRequestDTO dto, String passwordHash) {
        if (dto == null) return null;

        Customer customer = new Customer();
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setName(dto.name().trim());
        customer.setEmail(dto.email().trim().toLowerCase(Locale.ROOT));
        customer.setPasswordHash(passwordHash);
        return customer;
    }

    public CustomerResponseDTO toResponse(Customer customer) {
        if (customer == null) return null;

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getStatus(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}
