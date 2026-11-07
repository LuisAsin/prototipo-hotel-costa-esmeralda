package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Respuesta simplificada para la reserva desde la página pública. */
public record ReservaPublicaResponse(
        Long id,
        String codigo,
        String estado,
        String titular,
        LocalDate fechaIngreso,
        LocalDate fechaSalida,
        Integer cantidadHabitaciones,
        BigDecimal total
) {
}
