package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import pe.edu.idat.costaesmeralda.model.EstadoDetalle;

/** Detalle (habitación) de una reserva. */
public record DetalleReservaResponse(
        Long id,
        Long habitacionId,
        String habitacionNumero,
        Long tipoHabitacionId,
        String nombreTipo,
        LocalDate fechaIngreso,
        LocalDate fechaSalida,
        Integer noches,
        Integer cantidadHuespedes,
        BigDecimal tarifaNoche,
        BigDecimal importeHabitacion,
        EstadoDetalle estado,
        Boolean cancelable,
        String motivoNoCancelable,
        List<ServicioReservaResponse> servicios
) {
}
