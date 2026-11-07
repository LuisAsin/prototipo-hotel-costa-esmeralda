package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import pe.edu.idat.costaesmeralda.model.EstadoPago;

/** Pago PENDIENTE o EN_VERIFICACION; requiereConciliacion si supera 30 min. */
public record PagoVerificacionResponse(
        Long pagoId,
        Long reservaId,
        String codigoReserva,
        BigDecimal monto,
        EstadoPago estado,
        LocalDateTime creadoEn,
        Long minutosEnVerificacion,
        Boolean requiereConciliacion
) {
}
