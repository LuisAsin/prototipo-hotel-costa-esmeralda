package pe.edu.idat.costaesmeralda.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/** Solicitud de cotización del carrito; el carrito no retiene inventario (RN-08). */
public record CotizacionRequest(
        @NotEmpty @Valid List<ItemCarritoRequest> items,
        String codigoCupon
) {
}
