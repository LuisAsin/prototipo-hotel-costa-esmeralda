package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.ModalidadServicio;

/** Servicio cotizado; importe = precio × cantidad × habitaciones del ítem. */
public record ServicioCotizado(
        Long servicioId,
        String nombre,
        ModalidadServicio modalidad,
        Integer cantidad,
        BigDecimal importe
) {
}
