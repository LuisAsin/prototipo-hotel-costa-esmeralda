package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

/** Ítem del carrito. Fechas (RN-03) y capacidad se comprueban en el servicio (400 con campo). */
public record ItemCarritoRequest(
        @NotNull Long tipoHabitacionId,
        @NotNull @Min(1) Integer cantidad,
        @NotNull LocalDate fechaIngreso,
        @NotNull LocalDate fechaSalida,
        @NotNull @Min(1) Integer huespedesPorHabitacion,
        List<Long> serviciosIds
) {
}
