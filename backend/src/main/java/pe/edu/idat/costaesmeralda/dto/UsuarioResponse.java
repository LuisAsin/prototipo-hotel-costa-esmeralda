package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDateTime;
import pe.edu.idat.costaesmeralda.model.Rol;

/** Usuario simulado. */
public record UsuarioResponse(
        Long id,
        String nombres,
        String apellidos,
        String nombreCompleto,
        String correo,
        String documento,
        String telefono,
        Rol rol,
        Boolean consentimientoDatos,
        LocalDateTime fechaRegistro
) {
}
