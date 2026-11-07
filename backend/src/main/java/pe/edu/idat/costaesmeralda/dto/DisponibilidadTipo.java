package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;

/** Disponibilidad de un tipo; importeEstimado = noches × tarifa, 1 habitación, sin descuento. */
public record DisponibilidadTipo(
        Long tipoHabitacionId,
        String nombre,
        Integer capacidad,
        BigDecimal tarifaNoche,
        Integer disponibles,
        BigDecimal importeEstimado,
        OfertaAplicable ofertaAplicable
) {
}
