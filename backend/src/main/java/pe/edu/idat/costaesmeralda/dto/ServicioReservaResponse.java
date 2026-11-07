package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.ModalidadServicio;

/** Servicio contratado en un detalle de la reserva. */
public record ServicioReservaResponse(
        Long id,
        Long servicioId,
        String nombre,
        ModalidadServicio modalidad,
        BigDecimal precioUnitario,
        Integer cantidad,
        BigDecimal importe
) {
}
