package pe.edu.idat.costaesmeralda.dto;

/** Fila del reporte: cantidad por clave (estado o canal de la reserva). */
public record ConteoClave(
        String clave,
        Long cantidad
) {
}
