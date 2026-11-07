package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import pe.edu.idat.costaesmeralda.model.ResultadoConciliacion;

/** Conciliación manual de un pago EN_VERIFICACION (RN-34). Solo APROBADO o RECHAZADO. */
public record ConciliarPagoRequest(
        @NotNull ResultadoConciliacion resultado,
        @NotBlank String motivo
) {
}
