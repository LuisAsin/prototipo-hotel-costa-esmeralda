package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/** Bloqueo de una habitación; fechaFin > fechaInicio se comprueba en el servicio (400 campo fechaFin). */
public record CrearBloqueoRequest(
        @NotNull Long habitacionId,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFin,
        @NotBlank String motivo
) {
}
