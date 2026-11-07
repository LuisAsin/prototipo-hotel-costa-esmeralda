package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.idat.costaesmeralda.model.Canal;
import pe.edu.idat.costaesmeralda.model.ModalidadConfirmacion;
import pe.edu.idat.costaesmeralda.model.EstadoReserva;

/** Comprobante interno: copia inmutable de la emisión más estado actual y anotaciones (D13, D13b). */
public record ComprobanteResponse(
        // Copia inmutable de la emisión
        String numero,
        LocalDateTime fechaEmision,
        Long reservaId,
        String codigoReserva,
        String titularNombre,
        String titularDocumento,
        String titularCorreo,
        String titularTelefono,
        Canal canal,
        ModalidadConfirmacion modalidad,
        List<ComprobanteHabitacion> habitaciones,
        List<ComprobanteServicio> servicios,
        BigDecimal subtotalHabitaciones,
        BigDecimal subtotalServicios,
        BigDecimal subtotal,
        BigDecimal descuento,
        String descripcionDescuento,
        BigDecimal total,
        String rotulo,
        // Estado actual, fuera de la copia
        EstadoReserva estadoReserva,
        List<AnotacionComprobante> anotaciones
) {
}
