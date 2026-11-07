package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.EstadoReserva;

/** Resultado de una cancelación total, parcial o no presentación. */
public record CancelacionResponse(
        Long reservaId,
        String codigoReserva,
        EstadoReserva estadoReserva,
        Long detalleId,
        BigDecimal importeCancelado,
        BigDecimal penalidad,
        BigDecimal montoDevolucion,
        BigDecimal nuevoTotal,
        String mensaje
) {
}
