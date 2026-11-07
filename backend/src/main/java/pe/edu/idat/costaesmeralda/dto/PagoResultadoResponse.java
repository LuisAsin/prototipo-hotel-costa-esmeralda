package pe.edu.idat.costaesmeralda.dto;

import pe.edu.idat.costaesmeralda.model.EstadoReserva;
import pe.edu.idat.costaesmeralda.model.EstadoPago;

/** Resultado de un intento de pago; esRepetido indica respuesta idempotente (RN-30). */
public record PagoResultadoResponse(
        Long pagoId,
        Long reservaId,
        EstadoPago estado,
        String mensaje,
        EstadoReserva reservaEstado,
        String codigoReserva,
        String comprobanteNumero,
        Boolean esRepetido
) {
}
