package pe.edu.idat.costaesmeralda.controller;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.validation.Valid;
import pe.edu.idat.costaesmeralda.dto.CrearReservaRequest;
import pe.edu.idat.costaesmeralda.dto.CeldaOcupacionResponse;
import pe.edu.idat.costaesmeralda.dto.FilaOcupacionResponse;
import pe.edu.idat.costaesmeralda.dto.HabitacionCatalogoPublicaResponse;
import pe.edu.idat.costaesmeralda.dto.ItemCarritoRequest;
import pe.edu.idat.costaesmeralda.dto.MapaOcupacionResponse;
import pe.edu.idat.costaesmeralda.dto.PagoFicticioResponse;
import pe.edu.idat.costaesmeralda.dto.ReservaPublicaResponse;
import pe.edu.idat.costaesmeralda.dto.SolicitarTokenPagoRequest;
import pe.edu.idat.costaesmeralda.dto.TokenPagoResponse;
import pe.edu.idat.costaesmeralda.dto.TipoHabitacionResponse;
import pe.edu.idat.costaesmeralda.dto.ValidarTokenPagoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** Endpoints públicos del catálogo y flujo de reservas del prototipo. */
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class CatalogoReservaController {
    private final List<TipoHabitacionResponse> habitaciones = List.of(
            new TipoHabitacionResponse(1L, "Habitación Simple", "Cama de 1.5 plazas, baño privado y TV.", 1,
                    new BigDecimal("120.00"), List.of("Baño privado", "TV", "Wi-Fi"), "/assets/img/simple.jpg"),
            new TipoHabitacionResponse(2L, "Habitación Doble", "Dos camas individuales, minibar y vista al jardín.", 2,
                    new BigDecimal("180.00"), List.of("Minibar", "Vista al jardín", "Wi-Fi"), "/assets/img/doble.jpg"),
            new TipoHabitacionResponse(3L, "Habitación Matrimonial", "Cama King size, jacuzzi y balcón privado.", 2,
                    new BigDecimal("220.00"), List.of("Cama King", "Jacuzzi", "Balcón privado"), "/assets/img/matrimonial.jpg")
    );
    private final AtomicLong secuencia = new AtomicLong(1000);
    private final Map<Long, ReservaPublicaResponse> reservas = new ConcurrentHashMap<>();
    private final Map<Long, List<OcupacionReserva>> ocupacionReservas = new ConcurrentHashMap<>();
    private final Map<String, Long> solicitudesRegistradas = new ConcurrentHashMap<>();
    private final Map<Long, TokenPago> tokensPago = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final Map<Long, String> estadoHabitaciones = new ConcurrentHashMap<>(Map.of(1L, "DISPONIBLE", 2L, "DISPONIBLE", 3L, "MANTENIMIENTO"));
    private final Map<Long, Integer> inventarioHabitaciones = Map.of(1L, 5, 2L, 4, 3L, 3);
    private final List<UnidadHotel> unidadesHotel = List.of(
            new UnidadHotel(1L, "101"), new UnidadHotel(1L, "102"), new UnidadHotel(1L, "103"), new UnidadHotel(1L, "104"), new UnidadHotel(1L, "105"),
            new UnidadHotel(2L, "201"), new UnidadHotel(2L, "202"), new UnidadHotel(2L, "203"), new UnidadHotel(2L, "204"),
            new UnidadHotel(3L, "301"), new UnidadHotel(3L, "302"), new UnidadHotel(3L, "303"));
    private final Map<String, String> contenido = new ConcurrentHashMap<>(Map.of(
            "titulo", "Bienvenido a Costa Esmeralda",
            "subtitulo", "Tu descanso perfecto está a un clic de distancia",
            "telefono", "+51 987 654 321",
            "correo", "reservas@costaesmeralda.com"));

    @PostMapping("/auth/login")
    public Map<String, Object> iniciarSesion(@RequestBody Map<String, String> credenciales) {
        if (!"admin".equals(credenciales.get("usuario")) || !"1234".equals(credenciales.get("contrasena"))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }
        return Map.of("token", "demo-admin-token", "usuario", "admin", "rol", "ADMIN");
    }

    @GetMapping("/habitaciones")
    public List<HabitacionCatalogoPublicaResponse> listarHabitaciones(
            @RequestParam(required = false) LocalDate fechaIngreso,
            @RequestParam(required = false) LocalDate fechaSalida) {
        if ((fechaIngreso == null) != (fechaSalida == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Envía ambas fechas para consultar disponibilidad");
        }
        LocalDate desde = fechaIngreso == null ? LocalDate.now() : fechaIngreso;
        LocalDate hasta = fechaSalida == null ? desde.plusDays(1) : fechaSalida;
        if (!hasta.isAfter(desde)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La salida debe ser posterior al ingreso");
        return habitaciones.stream().map(habitacion -> {
            String estado = estadoHabitaciones.getOrDefault(habitacion.id(), "DISPONIBLE");
            int inventario = inventarioHabitaciones.getOrDefault(habitacion.id(), 0);
            int ocupadas = habitacionesOcupadas(habitacion.id(), desde, hasta);
            int disponibles = "DISPONIBLE".equals(estado) ? Math.max(0, inventario - ocupadas) : 0;
            return new HabitacionCatalogoPublicaResponse(habitacion.id(), habitacion.nombre(), habitacion.descripcion(),
                    habitacion.capacidad(), habitacion.tarifaBase(), habitacion.caracteristicas(), habitacion.imagenUrl(),
                    estado, disponibles, inventario);
        }).toList();
    }

    @GetMapping("/contenido")
    public Map<String, String> obtenerContenidoPublico() {
        return Map.copyOf(contenido);
    }

    @PostMapping("/reservas")
    public ResponseEntity<ReservaPublicaResponse> crearReserva(@Valid @RequestBody CrearReservaRequest request) {
        var guardado = registrarSolicitud(request, "PENDIENTE");
        return ResponseEntity.status(guardado.creado() ? HttpStatus.CREATED : HttpStatus.OK).body(guardado.reserva());
    }

    private synchronized RegistroReserva registrarSolicitud(CrearReservaRequest request, String estado) {
        String clave = claveSolicitud(request);
        Long idExistente = solicitudesRegistradas.get(clave);
        if (idExistente != null) {
            var existente = reservas.get(idExistente);
            if (existente != null && "CONFIRMADA".equals(estado) && "PENDIENTE".equals(existente.estado())) {
                existente = new ReservaPublicaResponse(existente.id(), existente.codigo(), "CONFIRMADA", existente.titular(),
                        existente.fechaIngreso(), existente.fechaSalida(), existente.cantidadHabitaciones(), existente.total());
                reservas.put(idExistente, existente);
            }
            return new RegistroReserva(existente, false);
        }

        var fechaIngreso = request.items().stream().map(ItemCarritoRequest::fechaIngreso).min(java.time.LocalDate::compareTo).orElseThrow();
        var fechaSalida = request.items().stream().map(ItemCarritoRequest::fechaSalida).max(java.time.LocalDate::compareTo).orElseThrow();
        BigDecimal total = BigDecimal.ZERO;
        for (var item : request.items()) {
            var tipo = habitaciones.stream().filter(h -> h.id().equals(item.tipoHabitacionId())).findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de habitación no encontrado"));
            if (!item.fechaSalida().isAfter(item.fechaIngreso())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de salida debe ser posterior al ingreso");
            }
            String estadoTipo = estadoHabitaciones.getOrDefault(tipo.id(), "DISPONIBLE");
            if (!"DISPONIBLE".equals(estadoTipo)) {
                String motivo = "MANTENIMIENTO".equals(estadoTipo) ? "está en mantenimiento" : "está en limpieza";
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        tipo.nombre() + " " + motivo + ". Selecciona otra habitación.");
            }
            if (item.huespedesPorHabitacion() > tipo.capacidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad de huéspedes supera la capacidad");
            }
            int solicitadasEnCruce = request.items().stream()
                    .filter(otra -> otra.tipoHabitacionId().equals(item.tipoHabitacionId()))
                    .filter(otra -> otra.fechaIngreso().isBefore(item.fechaSalida()) && otra.fechaSalida().isAfter(item.fechaIngreso()))
                    .mapToInt(ItemCarritoRequest::cantidad).sum();
            int yaOcupadas = habitacionesOcupadas(tipo.id(), item.fechaIngreso(), item.fechaSalida());
            if (yaOcupadas + solicitadasEnCruce > inventarioHabitaciones.getOrDefault(tipo.id(), 0)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No hay disponibilidad para " + tipo.nombre() + " en las fechas seleccionadas.");
            }
            long noches = ChronoUnit.DAYS.between(item.fechaIngreso(), item.fechaSalida());
            total = total.add(tipo.tarifaBase().multiply(BigDecimal.valueOf(noches))
                    .multiply(BigDecimal.valueOf(item.cantidad())));
        }
        long id = secuencia.incrementAndGet();
        var reserva = new ReservaPublicaResponse(id, "CE-" + id, estado, request.titular().nombre(),
                fechaIngreso, fechaSalida, request.items().stream().mapToInt(ItemCarritoRequest::cantidad).sum(), total);
        reservas.put(id, reserva);
        var asignaciones = new ArrayList<OcupacionReserva>();
        for (var item : request.items()) {
            for (int i = 0; i < item.cantidad(); i++) {
                var unidad = unidadesHotel.stream().filter(u -> u.tipoHabitacionId().equals(item.tipoHabitacionId()))
                        .filter(u -> !unidadOcupada(u.numero(), item.fechaIngreso(), item.fechaSalida(), asignaciones))
                        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                                "No queda una unidad física disponible para las fechas seleccionadas"));
                asignaciones.add(new OcupacionReserva(item.tipoHabitacionId(), unidad.numero(), 1,
                        item.fechaIngreso(), item.fechaSalida()));
            }
        }
        ocupacionReservas.put(id, List.copyOf(asignaciones));
        solicitudesRegistradas.put(clave, id);
        return new RegistroReserva(reserva, true);
    }

    private String claveSolicitud(CrearReservaRequest request) {
        String items = request.items().stream()
                .map(item -> item.tipoHabitacionId() + ":" + item.cantidad() + ":" + item.fechaIngreso() + ":"
                        + item.fechaSalida() + ":" + item.huespedesPorHabitacion() + ":"
                        + (item.serviciosIds() == null ? "" : item.serviciosIds().stream().sorted().map(String::valueOf).collect(java.util.stream.Collectors.joining(","))))
                .sorted(Comparator.naturalOrder())
                .collect(java.util.stream.Collectors.joining("|"));
        String datos = request.titular().documento() + "|" + items;
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(datos.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no está disponible", e);
        }
    }

    @PostMapping("/admin/recepcion")
    public ResponseEntity<ReservaPublicaResponse> registrarRecepcion(@RequestHeader("Authorization") String auth,
            @Valid @RequestBody CrearReservaRequest request) {
        autorizar(auth);
        var guardado = registrarSolicitud(request, "CONFIRMADA");
        return ResponseEntity.status(guardado.creado() ? HttpStatus.CREATED : HttpStatus.OK).body(guardado.reserva());
    }

    @GetMapping("/reservas/{id}")
    public ReservaPublicaResponse obtenerReserva(@PathVariable Long id) {
        var reserva = reservas.get(id);
        if (reserva == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        return reserva;
    }

    @PostMapping("/pagos/ficticio/token")
    public synchronized TokenPagoResponse solicitarTokenPago(@Valid @RequestBody SolicitarTokenPagoRequest request) {
        var reserva = reservas.get(request.reservaId());
        if (reserva == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        if (!"PENDIENTE".equals(reserva.estado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La reserva no está pendiente de pago");
        }

        Instant ahora = Instant.now();
        var tokenActual = tokensPago.get(reserva.id());
        if (tokenActual != null && tokenActual.expiraEn().isAfter(ahora)) {
            if (tokenActual.intentos() >= 3) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Se agotaron los intentos. Solicita ayuda para continuar");
            }
            return new TokenPagoResponse(reserva.id(), reserva.codigo(), tokenActual.codigo(), tokenActual.expiraEn(), "SIMULACIÓN");
        }

        String codigo = String.format(java.util.Locale.ROOT, "%06d", random.nextInt(1_000_000));
        Instant expiracion = ahora.plusSeconds(300);
        tokensPago.put(reserva.id(), new TokenPago(codigo, expiracion, 0, false));
        return new TokenPagoResponse(reserva.id(), reserva.codigo(), codigo, expiracion, "SIMULACIÓN");
    }

    @PostMapping("/pagos/ficticio/validar")
    public synchronized PagoFicticioResponse validarTokenPago(@Valid @RequestBody ValidarTokenPagoRequest request) {
        var reserva = reservas.get(request.reservaId());
        if (reserva == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        var tokenPago = tokensPago.get(reserva.id());
        if (tokenPago == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primero solicita un token de validación");

        boolean coincide = MessageDigest.isEqual(request.token().getBytes(StandardCharsets.US_ASCII),
                tokenPago.codigo().getBytes(StandardCharsets.US_ASCII));
        if (tokenPago.utilizado() && "CONFIRMADA".equals(reserva.estado()) && coincide) {
            return new PagoFicticioResponse(reserva.id(), reserva.codigo(), reserva.estado(), "APROBADO",
                    "El pago ficticio ya había sido validado.");
        }
        if (!tokenPago.expiraEn().isAfter(Instant.now())) {
            tokensPago.remove(reserva.id());
            throw new ResponseStatusException(HttpStatus.GONE, "El token expiró. Solicita uno nuevo");
        }
        if (tokenPago.intentos() >= 3) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Se agotaron los intentos de validación");
        }
        if (!coincide) {
            int intentos = tokenPago.intentos() + 1;
            tokensPago.put(reserva.id(), new TokenPago(tokenPago.codigo(), tokenPago.expiraEn(), intentos, false));
            if (intentos >= 3) throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Token incorrecto. Se agotaron los intentos");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token incorrecto. Te quedan " + (3 - intentos) + " intentos");
        }
        if (!"PENDIENTE".equals(reserva.estado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La reserva no admite el pago en su estado actual");
        }

        var confirmada = new ReservaPublicaResponse(reserva.id(), reserva.codigo(), "CONFIRMADA", reserva.titular(),
                reserva.fechaIngreso(), reserva.fechaSalida(), reserva.cantidadHabitaciones(), reserva.total());
        reservas.put(reserva.id(), confirmada);
        tokensPago.put(reserva.id(), new TokenPago(tokenPago.codigo(), tokenPago.expiraEn(), tokenPago.intentos(), true));
        return new PagoFicticioResponse(confirmada.id(), confirmada.codigo(), confirmada.estado(), "APROBADO",
                "Pago ficticio validado correctamente. No se realizó ningún cargo.");
    }

    @GetMapping("/admin/resumen")
    public Map<String, Object> resumenAdmin(@RequestHeader("Authorization") String auth) {
        autorizar(auth);
        long activas = reservas.values().stream().filter(r -> !"CANCELADA".equals(r.estado())).count();
        long habitacionesDisponibles = estadoHabitaciones.values().stream().filter("DISPONIBLE"::equals).count();
        BigDecimal ingresos = reservas.values().stream().filter(r -> "CONFIRMADA".equals(r.estado()))
                .map(ReservaPublicaResponse::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of("reservasActivas", activas, "habitacionesDisponibles", habitacionesDisponibles,
                "habitacionesTotales", habitaciones.size(), "ingresos", ingresos, "reservas", reservas.size());
    }

    @GetMapping("/admin/reservas")
    public List<ReservaPublicaResponse> listarReservasAdmin(@RequestHeader("Authorization") String auth) {
        autorizar(auth);
        return reservas.values().stream().sorted((a, b) -> b.id().compareTo(a.id())).toList();
    }

    @GetMapping("/admin/ocupacion")
    public MapaOcupacionResponse mapaOcupacion(@RequestHeader("Authorization") String auth,
            @RequestParam LocalDate desde, @RequestParam(defaultValue = "7") int dias) {
        autorizar(auth);
        if (dias < 1 || dias > 31) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rango debe ser de 1 a 31 días");
        var fechas = java.util.stream.IntStream.range(0, dias).mapToObj(desde::plusDays).toList();
        var filas = unidadesHotel.stream().map(unidad -> {
            var habitacion = habitaciones.stream().filter(h -> h.id().equals(unidad.tipoHabitacionId())).findFirst().orElseThrow();
            String estadoHabitacion = estadoHabitaciones.getOrDefault(habitacion.id(), "DISPONIBLE");
            var celdas = fechas.stream().map(fecha -> {
                var reservasDelDia = reservas.values().stream()
                        .filter(reserva -> "PENDIENTE".equals(reserva.estado()) || "CONFIRMADA".equals(reserva.estado()))
                        .filter(reserva -> reserva.fechaIngreso().isBefore(fecha.plusDays(1)) && reserva.fechaSalida().isAfter(fecha))
                        .filter(reserva -> ocupacionReservas.getOrDefault(reserva.id(), List.of()).stream()
                                .anyMatch(asignacion -> asignacion.habitacionNumero().equals(unidad.numero())
                                        && asignacion.fechaIngreso().isBefore(fecha.plusDays(1)) && asignacion.fechaSalida().isAfter(fecha)))
                        .toList();
                int ocupadas = reservasDelDia.stream().mapToInt(reserva -> ocupacionReservas.getOrDefault(reserva.id(), List.of()).stream()
                        .filter(asignacion -> asignacion.habitacionNumero().equals(unidad.numero())
                                && asignacion.fechaIngreso().isBefore(fecha.plusDays(1)) && asignacion.fechaSalida().isAfter(fecha))
                        .mapToInt(OcupacionReserva::cantidad).sum()).sum();
                String estadoCelda = "DISPONIBLE".equals(estadoHabitacion)
                        ? (ocupadas > 0 ? "OCUPADA" : "DISPONIBLE") : estadoHabitacion;
                int disponibles = "DISPONIBLE".equals(estadoHabitacion) && ocupadas == 0 ? 1 : 0;
                return new CeldaOcupacionResponse(fecha, estadoCelda, ocupadas, disponibles,
                        reservasDelDia.stream().map(ReservaPublicaResponse::codigo).distinct().toList());
            }).toList();
            return new FilaOcupacionResponse(habitacion.id(), unidad.numero(), habitacion.nombre(), estadoHabitacion, 1, celdas);
        }).toList();
        return new MapaOcupacionResponse(desde, fechas, filas);
    }

    @PatchMapping("/admin/reservas/{id}/estado")
    public ReservaPublicaResponse cambiarEstadoReserva(@RequestHeader("Authorization") String auth,
            @PathVariable Long id, @RequestBody Map<String, String> datos) {
        autorizar(auth);
        var actual = reservas.get(id);
        if (actual == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        String estado = datos.get("estado");
        if (!List.of("CONFIRMADA", "CANCELADA", "COMPLETADA").contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido");
        }
        var actualizada = new ReservaPublicaResponse(actual.id(), actual.codigo(), estado, actual.titular(),
                actual.fechaIngreso(), actual.fechaSalida(), actual.cantidadHabitaciones(), actual.total());
        reservas.put(id, actualizada);
        return actualizada;
    }

    @GetMapping("/admin/habitaciones/estado")
    public Map<Long, String> listarEstadosHabitaciones(@RequestHeader("Authorization") String auth) {
        autorizar(auth);
        return Map.copyOf(estadoHabitaciones);
    }

    @PutMapping("/admin/habitaciones/{id}/estado")
    public Map<String, String> cambiarEstadoHabitacion(@RequestHeader("Authorization") String auth,
            @PathVariable Long id, @RequestBody Map<String, String> datos) {
        autorizar(auth);
        if (habitaciones.stream().noneMatch(h -> h.id().equals(id))) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Habitación no encontrada");
        String estado = datos.get("estado");
        if (!List.of("DISPONIBLE", "MANTENIMIENTO", "LIMPIEZA").contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido");
        }
        estadoHabitaciones.put(id, estado);
        return Map.of("id", id.toString(), "estado", estado);
    }

    @GetMapping("/admin/contenido")
    public Map<String, String> obtenerContenido(@RequestHeader("Authorization") String auth) {
        autorizar(auth);
        return Map.copyOf(contenido);
    }

    @PutMapping("/admin/contenido")
    public Map<String, String> actualizarContenido(@RequestHeader("Authorization") String auth,
            @RequestBody Map<String, String> datos) {
        autorizar(auth);
        contenido.putAll(datos);
        return Map.copyOf(contenido);
    }

    @GetMapping("/admin/reportes")
    public Map<String, Object> reportes(@RequestHeader("Authorization") String auth) {
        autorizar(auth);
        BigDecimal total = reservas.values().stream().filter(r -> !"CANCELADA".equals(r.estado()))
                .map(ReservaPublicaResponse::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of("reservas", reservas.size(), "ingresosEstimados", total,
                "canalPrincipal", "Página Web");
    }

    private void autorizar(String authorization) {
        if (!"Bearer demo-admin-token".equals(authorization)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión administrativa no válida");
        }
    }

    private record RegistroReserva(ReservaPublicaResponse reserva, boolean creado) {}
    private record TokenPago(String codigo, Instant expiraEn, int intentos, boolean utilizado) {}
    private record UnidadHotel(Long tipoHabitacionId, String numero) {}
    private record OcupacionReserva(Long tipoHabitacionId, String habitacionNumero, Integer cantidad,
            LocalDate fechaIngreso, LocalDate fechaSalida) {}

    private int habitacionesOcupadas(Long tipoHabitacionId, LocalDate desde, LocalDate hasta) {
        return reservas.values().stream()
                .filter(reserva -> "PENDIENTE".equals(reserva.estado()) || "CONFIRMADA".equals(reserva.estado()))
                .filter(reserva -> reserva.fechaIngreso().isBefore(hasta) && reserva.fechaSalida().isAfter(desde))
                .mapToInt(reserva -> ocupacionReservas.getOrDefault(reserva.id(), List.of()).stream()
                        .filter(asignacion -> asignacion.tipoHabitacionId().equals(tipoHabitacionId)
                                && asignacion.fechaIngreso().isBefore(hasta) && asignacion.fechaSalida().isAfter(desde))
                        .mapToInt(OcupacionReserva::cantidad).sum())
                .sum();
    }

    private boolean unidadOcupada(String numero, LocalDate desde, LocalDate hasta, List<OcupacionReserva> asignacionesNuevas) {
        boolean ocupadaEnReservaActual = asignacionesNuevas.stream().anyMatch(asignacion -> asignacion.habitacionNumero().equals(numero)
                && asignacion.fechaIngreso().isBefore(hasta) && asignacion.fechaSalida().isAfter(desde));
        if (ocupadaEnReservaActual) return true;
        return reservas.values().stream()
                .filter(reserva -> "PENDIENTE".equals(reserva.estado()) || "CONFIRMADA".equals(reserva.estado()))
                .anyMatch(reserva -> ocupacionReservas.getOrDefault(reserva.id(), List.of()).stream()
                        .anyMatch(asignacion -> asignacion.habitacionNumero().equals(numero)
                                && asignacion.fechaIngreso().isBefore(hasta) && asignacion.fechaSalida().isAfter(desde)));
    }
}
