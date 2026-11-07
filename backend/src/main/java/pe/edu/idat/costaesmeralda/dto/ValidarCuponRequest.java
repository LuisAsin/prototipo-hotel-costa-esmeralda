package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotBlank;

/** Validación informativa de un cupón; el código se normaliza a mayúsculas en el servicio. */
public record ValidarCuponRequest(
        @NotBlank String codigo
) {
}
