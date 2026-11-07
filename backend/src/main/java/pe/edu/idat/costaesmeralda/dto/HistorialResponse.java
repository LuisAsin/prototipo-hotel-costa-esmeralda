package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDateTime;
import pe.edu.idat.costaesmeralda.model.TipoEntidadHistorial;

/** Registro del historial; usuarioNombre es «Sistema» si lo generó el job. */
public record HistorialResponse(
        Long id,
        TipoEntidadHistorial tipoEntidad,
        Long entidadId,
        String estadoAnterior,
        String estadoNuevo,
        Long usuarioId,
        String usuarioNombre,
        LocalDateTime fecha,
        String motivo
) {
}
