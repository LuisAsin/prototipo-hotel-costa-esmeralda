package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Reporte operativo; los montos son simulados. */
public record ReporteOperativoResponse(
        LocalDate desde,
        LocalDate hasta,
        Integer totalHabitaciones,
        List<OcupacionDia> ocupacionPorDia,
        List<ConteoClave> reservasPorEstado,
        List<ConteoClave> reservasPorCanal,
        List<PagosPorEstado> pagosPorEstado,
        BigDecimal montoAprobado,
        String rotuloMontos
) {
}
