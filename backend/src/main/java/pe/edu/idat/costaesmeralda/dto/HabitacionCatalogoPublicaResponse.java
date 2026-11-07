package pe.edu.idat.costaesmeralda.dto;

import java.math.BigDecimal;
import java.util.List;

/** Tipo de habitación con estado y disponibilidad para el catálogo público. */
public record HabitacionCatalogoPublicaResponse(
        Long id,
        String nombre,
        String descripcion,
        Integer capacidad,
        BigDecimal tarifaBase,
        List<String> caracteristicas,
        String imagenUrl,
        String estado,
        Integer disponibles,
        Integer inventario
) {}
