package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

/** Registro de un huésped nuevo (endpoint #2). */
public record RegistroUsuarioRequest(
        @NotBlank String nombres,
        @NotBlank String apellidos,
        @NotBlank @Email String correo,
        @NotBlank @Pattern(regexp = "^\\d{8}$", message = "El documento debe tener 8 dígitos") String documento,
        @NotBlank @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe tener 9 dígitos y empezar con 9") String telefono,
        @NotNull @AssertTrue(message = "Debes aceptar el tratamiento de datos") Boolean consentimientoDatos
) {
}
