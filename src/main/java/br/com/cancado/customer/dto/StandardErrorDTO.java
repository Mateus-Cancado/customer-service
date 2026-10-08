package br.com.cancado.customer.dto;

import java.time.Instant;

public record StandardErrorDTO(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}
