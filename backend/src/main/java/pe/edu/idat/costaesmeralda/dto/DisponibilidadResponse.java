package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import java.util.List;

/** Resultado de la búsqueda de disponibilidad. */
public record DisponibilidadResponse(
        LocalDate fechaIngreso,
        LocalDate fechaSalida,
        Integer noches,
        List<DisponibilidadTipo> tipos
) {
}
