package pe.edu.idat.costaesmeralda.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

/** Creación de una reserva WEB; nace PENDIENTE con retención (RN-25). */
public record CrearReservaRequest(
        @NotEmpty @Valid List<ItemCarritoRequest> items,
        String codigoCupon,
        @NotNull @Valid TitularRequest titular
) {
}
