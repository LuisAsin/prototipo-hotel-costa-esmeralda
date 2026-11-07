package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import pe.edu.idat.costaesmeralda.model.TipoPromocion;
import pe.edu.idat.costaesmeralda.model.TipoDescuento;

/** Promoción (oferta automática o cupón con código). */
public record PromocionResponse(
        Long id,
        String nombre,
        TipoPromocion tipo,
        String codigo,
        TipoDescuento tipoDescuento,
        BigDecimal valor,
        String descripcion,
        LocalDate vigenciaInicio,
        LocalDate vigenciaFin,
        Long tipoHabitacionId,
        String nombreTipoHabitacion,
        Integer estanciaMinimaNoches,
        Integer usosMaximos,
        Integer usosActuales,
        Boolean activa
) {
}
