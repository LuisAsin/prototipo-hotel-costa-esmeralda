package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;

/** Simulación de cancelación: penalidad de 0 o 50 % según el umbral de 48 h (RN-46). */
public record SimulacionCancelacionResponse(
        Long reservaId,
        Long detalleId,
        Boolean cancelable,
        String motivoNoCancelable,
        Long horasAnticipacion,
        Boolean conPenalidad,
        Integer porcentajePenalidad,
        BigDecimal importeCancelado,
        BigDecimal montoPagado,
        BigDecimal penalidad,
        BigDecimal montoDevolucion,
        BigDecimal nuevoTotal
) {
}
