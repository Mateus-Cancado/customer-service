package br.com.cancado.customer.dto;

public record TokenResponseDTO(
        String accessToken,
        String tokenType,
        long expiresIn
) {
    @Override
    public String toString() {
        return "TokenResponseDTO[accessToken=****, tokenType="
                + tokenType
                + ", expiresIn="
                + expiresIn
                + "]";
    }
}
