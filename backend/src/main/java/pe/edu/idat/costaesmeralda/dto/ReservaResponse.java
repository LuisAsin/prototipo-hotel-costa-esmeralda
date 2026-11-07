package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.idat.costaesmeralda.model.Canal;
import pe.edu.idat.costaesmeralda.model.ModalidadConfirmacion;
import pe.edu.idat.costaesmeralda.model.EstadoReserva;

/** Reserva creada (PENDIENTE en web, CONFIRMADA en recepción). */
public record ReservaResponse(
        Long id,
        String codigo,
        EstadoReserva estado,
        Canal canal,
        ModalidadConfirmacion modalidad,
        LocalDateTime venceEn,
        Long segundosRestantes,
        LocalDateTime garantiaHasta,
        List<DetalleReservaResponse> detalles,
        BigDecimal subtotalHabitaciones,
        BigDecimal subtotalServicios,
        BigDecimal subtotal,
        BigDecimal descuento,
        String descripcionDescuento,
        BigDecimal total,
        String comprobanteNumero,
        List<String> avisos
) {
}
