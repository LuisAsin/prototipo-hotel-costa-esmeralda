package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.util.List;

/** Cotización oficial calculada en el backend. */
public record CotizacionResponse(
        List<ItemCotizacion> items,
        BigDecimal subtotalHabitaciones,
        BigDecimal subtotalServicios,
        BigDecimal subtotal,
        DescuentoAplicado descuento,
        BigDecimal total,
        List<String> avisos,
        Boolean todoDisponible
) {
}
