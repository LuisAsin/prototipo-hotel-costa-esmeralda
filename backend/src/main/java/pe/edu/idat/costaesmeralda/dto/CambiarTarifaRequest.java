package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

/** Cambio de la tarifa base de un tipo de habitación. */
public record CambiarTarifaRequest(
        @NotNull @DecimalMin("0.01") BigDecimal tarifaBase
) {
}
