package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import pe.edu.idat.costaesmeralda.model.TipoDescuento;

/** Resultado informativo de validar un cupón; no reserva usos (RN-08). */
public record CuponResponse(
        Long promocionId,
        String codigo,
        String nombre,
        TipoDescuento tipoDescuento,
        BigDecimal valor,
        LocalDate vigenciaFin,
        Integer usosRestantes,
        String mensaje
) {
}
