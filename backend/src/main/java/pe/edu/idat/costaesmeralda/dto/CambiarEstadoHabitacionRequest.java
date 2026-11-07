package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;
import pe.edu.idat.costaesmeralda.model.EstadoHabitacion;

/** Cambio del estado operativo de una habitación. */
public record CambiarEstadoHabitacionRequest(
        @NotNull EstadoHabitacion estado
) {
}
