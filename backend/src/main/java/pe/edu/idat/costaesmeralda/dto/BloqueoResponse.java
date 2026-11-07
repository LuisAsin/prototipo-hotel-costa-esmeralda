package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Bloqueo administrativo de una habitación. */
public record BloqueoResponse(
        Long id,
        Long habitacionId,
        String habitacionNumero,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String motivo,
        Long creadoPor,
        String creadoPorNombre,
        LocalDateTime fechaCreacion
) {
}
