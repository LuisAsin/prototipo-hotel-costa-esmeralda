package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.idat.costaesmeralda.model.Canal;
import pe.edu.idat.costaesmeralda.model.ModalidadConfirmacion;
import pe.edu.idat.costaesmeralda.model.EstadoReserva;

/** Detalle completo de una reserva; comprobante no es null si se emitió, también si está CANCELADA (D13). */
public record ReservaDetalleResponse(
        Long id,
        String codigo,
        EstadoReserva estado,
        Canal canal,
        ModalidadConfirmacion modalidad,
        Long titularId,
        String titularNombre,
        String titularDocumento,
        String titularCorreo,
        String titularTelefono,
        Long registradoPorId,
        String registradoPorNombre,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaConfirmacion,
        LocalDateTime venceEn,
        Long segundosRestantes,
        LocalDateTime garantiaHasta,
        BigDecimal subtotalHabitaciones,
        BigDecimal subtotalServicios,
        BigDecimal subtotal,
        BigDecimal descuento,
        String descripcionDescuento,
        BigDecimal total,
        BigDecimal totalPagado,
        BigDecimal penalidad,
        BigDecimal montoDevolucion,
        Boolean cancelable,
        String motivoNoCancelable,
        Boolean estanciaIniciada,
        Boolean permiteNoPresentacion,
        List<DetalleReservaResponse> detalles,
        List<PagoResponse> pagos,
        List<HistorialResponse> historial,
        ComprobanteResponse comprobante
) {
}
