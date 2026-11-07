package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.EstadoPago;

/** Fila del reporte: pagos por estado con su monto. */
public record PagosPorEstado(
        EstadoPago estado,
        Long cantidad,
        BigDecimal monto
) {
}
