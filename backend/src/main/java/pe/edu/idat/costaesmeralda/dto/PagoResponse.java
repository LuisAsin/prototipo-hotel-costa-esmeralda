package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import pe.edu.idat.costaesmeralda.model.ProveedorPago;
import pe.edu.idat.costaesmeralda.model.EstadoPago;
import pe.edu.idat.costaesmeralda.model.MetodoPagoRecepcion;

/** Intento de pago de una reserva. */
public record PagoResponse(
        Long id,
        Long reservaId,
        ProveedorPago proveedor,
        MetodoPagoRecepcion metodo,
        BigDecimal monto,
        EstadoPago estado,
        String ultimos4,
        String idCargoExterno,
        String mensaje,
        LocalDateTime creadoEn,
        LocalDateTime actualizadoEn
) {
}
