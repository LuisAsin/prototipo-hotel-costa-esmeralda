package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Intento de pago web idempotente (RN-30). Sin monto, número, CVV ni vencimiento. */
public record PagoRequest(
        @NotNull Long reservaId,
        @NotBlank @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "La clave de idempotencia debe ser un UUID") String claveIdempotencia,
        @NotBlank @Pattern(regexp = "^tok_sim_[a-z]+_\\d{4}$", message = "Token de tarjeta simulado inválido") String tokenTarjeta,
        @NotBlank @Pattern(regexp = "^\\d{4}$", message = "Deben ser los 4 últimos dígitos") String ultimos4
) {
}
