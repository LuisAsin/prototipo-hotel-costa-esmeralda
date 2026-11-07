package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.idat.costaesmeralda.model.Canal;
import pe.edu.idat.costaesmeralda.model.ModalidadConfirmacion;
import pe.edu.idat.costaesmeralda.model.EstadoReserva;

/** Fila de listado de reservas; fechaIngreso es la mínima y fechaSalida la máxima. */
public record ReservaResumenResponse(
        Long id,
        String codigo,
        EstadoReserva estado,
        Canal canal,
        ModalidadConfirmacion modalidad,
        String titularNombre,
        LocalDate fechaIngreso,
        LocalDate fechaSalida,
        List<String> habitaciones,
        BigDecimal total,
        LocalDateTime fechaCreacion,
        LocalDateTime venceEn,
        LocalDateTime garantiaHasta,
        Boolean cancelable,
        Boolean permiteNoPresentacion
) {
}
