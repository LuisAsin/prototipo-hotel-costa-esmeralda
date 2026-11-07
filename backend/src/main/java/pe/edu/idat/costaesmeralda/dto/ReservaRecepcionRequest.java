package pe.edu.idat.costaesmeralda.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import pe.edu.idat.costaesmeralda.model.ModalidadRecepcion;
import pe.edu.idat.costaesmeralda.model.MetodoPagoRecepcion;

/** Reserva registrada en recepción. metodoPago es obligatorio si la modalidad es PAGO_RECEPCION: lo valida el servicio (400 con campo). */
public record ReservaRecepcionRequest(
        @NotEmpty @Valid List<ItemCarritoRequest> items,
        String codigoCupon,
        @NotNull @Valid TitularRequest titular,
        @NotNull ModalidadRecepcion modalidad,
        MetodoPagoRecepcion metodoPago
) {
}
