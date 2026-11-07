package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import pe.edu.idat.costaesmeralda.model.TipoAnotacion;

/** Anotación posterior a la emisión; nunca modifica la copia del comprobante (D13b). */
public record AnotacionComprobante(
        LocalDateTime fecha,
        TipoAnotacion tipo,
        String descripcion,
        BigDecimal importeCancelado,
        BigDecimal penalidad,
        BigDecimal montoDevolucion,
        BigDecimal nuevoTotal,
        String usuarioNombre
) {
}
