package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import pe.edu.idat.costaesmeralda.model.ModalidadServicio;

/** Servicio adicional del catálogo. */
public record ServicioAdicionalResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        ModalidadServicio modalidad,
        Boolean disponible
) {
}
