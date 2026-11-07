package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.ModalidadServicio;

/** Servicio del comprobante con los valores al emitir (D13b). */
public record ComprobanteServicio(
        String habitacionNumero,
        String nombre,
        ModalidadServicio modalidad,
        BigDecimal precioUnitario,
        Integer cantidad,
        BigDecimal importe
) {
}
