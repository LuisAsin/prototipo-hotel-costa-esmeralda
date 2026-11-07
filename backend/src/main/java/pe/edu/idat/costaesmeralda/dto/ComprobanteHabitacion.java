package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Habitación del comprobante con los valores al emitir (D13b). */
public record ComprobanteHabitacion(
        String habitacionNumero,
        String nombreTipo,
        LocalDate fechaIngreso,
        LocalDate fechaSalida,
        Integer noches,
        Integer cantidadHuespedes,
        BigDecimal tarifaNoche,
        BigDecimal importeHabitacion
) {
}
