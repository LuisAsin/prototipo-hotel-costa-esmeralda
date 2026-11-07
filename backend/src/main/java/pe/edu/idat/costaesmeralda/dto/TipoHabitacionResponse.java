package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.util.List;

/** Tipo de habitación del catálogo. */
public record TipoHabitacionResponse(
        Long id,
        String nombre,
        String descripcion,
        Integer capacidad,
        BigDecimal tarifaBase,
        List<String> caracteristicas,
        String imagenUrl
) {
}
