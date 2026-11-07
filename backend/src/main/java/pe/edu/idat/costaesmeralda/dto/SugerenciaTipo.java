package pe.edu.idat.costaesmeralda.dto;

/** Tipo alternativo sugerido cuando un ítem no está disponible. */
public record SugerenciaTipo(
        Long tipoHabitacionId,
        String nombreTipo,
        Integer disponibles
) {
}
