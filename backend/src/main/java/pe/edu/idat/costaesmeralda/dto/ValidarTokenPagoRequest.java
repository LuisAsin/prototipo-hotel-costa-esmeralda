package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ValidarTokenPagoRequest(
        @NotNull @Positive Long reservaId,
        @NotBlank @Pattern(regexp = "^\\d{6}$", message = "El token debe tener 6 dígitos") String token
) {}
