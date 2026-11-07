package pe.edu.idat.costaesmeralda.dto;

import java.util.List;

/** Respuesta de error normalizada. */
public record ErrorResponse(
        String codigo,
        String mensaje,
        String campo,
        List<String> detalles
) {
}
