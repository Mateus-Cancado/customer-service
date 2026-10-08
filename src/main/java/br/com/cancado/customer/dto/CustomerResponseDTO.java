package br.com.cancado.customer.dto;

import br.com.cancado.customer.enums.CustomerStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        CustomerStatus status,
        String name,
        String email,
        OffsetDateTime createdAt
) {}
