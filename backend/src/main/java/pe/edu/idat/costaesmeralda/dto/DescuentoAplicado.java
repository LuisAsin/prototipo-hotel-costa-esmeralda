package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.TipoPromocion;

/** Único descuento aplicado: el mayor entre oferta y cupón. */
// RN-18
public record DescuentoAplicado(
        Long promocionId,
        TipoPromocion tipo,
        String nombre,
        BigDecimal monto
) {
}
