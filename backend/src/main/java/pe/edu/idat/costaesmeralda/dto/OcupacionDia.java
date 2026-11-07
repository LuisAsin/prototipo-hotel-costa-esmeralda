package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Fila del reporte: ocupación de un día. */
public record OcupacionDia(
        LocalDate fecha,
        Integer habitacionesOcupadas,
        BigDecimal porcentaje
) {
}
