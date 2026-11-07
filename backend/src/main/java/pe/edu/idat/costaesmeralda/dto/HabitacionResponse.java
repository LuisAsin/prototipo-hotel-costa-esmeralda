package pe.edu.idat.costaesmeralda.dto;

import pe.edu.idat.costaesmeralda.model.EstadoHabitacion;

/** Habitación física del hotel. */
public record HabitacionResponse(
        Long id,
        String numero,
        Integer piso,
        Long tipoId,
        String nombreTipo,
        EstadoHabitacion estado
) {
}
