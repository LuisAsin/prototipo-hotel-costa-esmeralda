package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.util.List;

/** Ítem cotizado; importeHabitaciones = cantidad × noches × tarifa. */
public record ItemCotizacion(
        Integer indice,
        Long tipoHabitacionId,
        String nombreTipo,
        Integer cantidad,
        Integer noches,
        BigDecimal tarifaNoche,
        BigDecimal importeHabitaciones,
        List<ServicioCotizado> servicios,
        BigDecimal importeServicios,
        Boolean disponible,
        Integer disponibles,
        String mensaje,
        List<SugerenciaTipo> sugerencias
) {
}
