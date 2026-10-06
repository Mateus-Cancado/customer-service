package br.com.cancado.customer.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String email,
        OffsetDateTime createdAt
) {}
