import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { TipoHabitacionResponse } from './core/models/catalogo';
import { Carrito } from './pages/carrito/carrito';

interface ReservaPublica {
  id: number; codigo: string; estado: string; titular: string;
  fechaIngreso: string; fechaSalida: string; cantidadHabitaciones: number; total: number;
}
interface TokenPagoResponse { reservaId: number; codigoReserva: string; token: string; expiraEn: string; entorno: string; }
interface PagoFicticioResponse { reservaId: number; codigoReserva: string; estadoReserva: string; estadoPago: string; mensaje: string; }
interface CeldaOcupacion { fecha: string; estado: string; ocupadas: number; disponibles: number; reservas: string[]; }
interface FilaOcupacion { tipoHabitacionId: number; habitacionNumero: string; nombre: string; estado: string; inventario: number; dias: CeldaOcupacion[]; }
interface MapaOcupacion { desde: string; fechas: string[]; habitaciones: FilaOcupacion[]; }
type CarritoItem = TipoHabitacionResponse;
type PaginaSitio = 'inicio' | 'nosotros' | 'catalogo' | 'reservar' | 'servicios' | 'buffets' | 'contacto' | 'carrito';
type VistaAdmin = 'resumen' | 'calendario' | 'recepcion' | 'reservas' | 'mantenimiento' | 'reportes' | 'usuarios' | 'web';

@Component({ imports: [FormsModule, Carrito], selector: 'app-root', styleUrl: './app.css', templateUrl: './app.html' })
export class App implements OnInit {
  private readonly api = 'http://localhost:8080/api';
  private readonly storageKey = 'hotelCostaCarrito';
  readonly habitaciones = signal<TipoHabitacionResponse[]>([]);
  readonly carrito = signal<CarritoItem[]>(this.leerCarrito());
  readonly cargando = signal(true);
  readonly error = signal('');
  readonly aviso = signal('');
  readonly reserva = signal<ReservaPublica | null>(null);
  readonly vista = signal<'sitio' | 'login' | 'admin' | 'checkout'>('sitio');
  readonly pagina = signal<PaginaSitio>('inicio');
  readonly adminVista = signal<VistaAdmin>('resumen');
  readonly adminResumen = signal<Record<string, number>>({});
  readonly reservas = signal<ReservaPublica[]>([]);
  readonly estadosHabitaciones = signal<Record<string, string>>({});
  readonly mapaOcupacion = signal<MapaOcupacion>({ desde: '', fechas: [], habitaciones: [] });
  readonly reporte = signal<Record<string, string | number>>({});
  readonly contenido = signal<Record<string, string>>({});
  readonly enviandoReserva = signal(false);
  readonly tokenPago = signal<TokenPagoResponse | null>(null);
  readonly pagoConfirmado = signal(false);
  readonly enviandoPago = signal(false);
  readonly enviandoRecepcion = signal(false);
  readonly iniciandoSesion = signal(false);
  readonly estadoPorHabitacion: Record<number, string> = {};
  tipoHabitacionId = 1;
  fechaIngreso = '';
  fechaSalida = '';
  huespedes = 1;
  nombre = '';
  documento = '';
  correo = '';
  telefono = '';
  tokenIngresado = '';
  usuario = '';
  contrasena = '';
  private token = sessionStorage.getItem('hotelCostaToken') ?? '';

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void {
    this.cargarHabitaciones();
    this.http.get<Record<string, string>>(`${this.api}/contenido`).subscribe({ next: (data) => this.contenido.set(data) });
    if (this.token) { this.vista.set('admin'); this.cargarAdmin(); }
  }

  private leerCarrito(): CarritoItem[] {
    try { return JSON.parse(sessionStorage.getItem('hotelCostaCarrito') ?? '[]') as CarritoItem[]; }
    catch { return []; }
  }

  private guardarCarrito(): void { sessionStorage.setItem(this.storageKey, JSON.stringify(this.carrito())); }
  private headers(): HttpHeaders { return new HttpHeaders({ Authorization: `Bearer ${this.token}` }); }
  private errorApi(error: any): string { return error?.error?.detail ?? error?.error?.message ?? 'No se pudo completar la operación.'; }

  adminTitulo(): string {
    return ({ resumen: 'Dashboard principal', calendario: 'Mapa de ocupación', recepcion: 'Registro manual de recepción',
      reservas: 'Reservas web', mantenimiento: 'Bloqueos y mantenimiento', reportes: 'Reportes financieros',
      usuarios: 'Usuarios y permisos', web: 'Editar contenido de la web' })[this.adminVista()];
  }

  cargarHabitaciones(fechaIngreso?: string, fechaSalida?: string): void {
    let params = new HttpParams();
    if (fechaIngreso && fechaSalida) params = params.set('fechaIngreso', fechaIngreso).set('fechaSalida', fechaSalida);
    this.http.get<TipoHabitacionResponse[]>(`${this.api}/habitaciones`, { params }).subscribe({
      next: (items) => { this.habitaciones.set(items); this.cargando.set(false); if (!items.some((item) => item.id === this.tipoHabitacionId) && items.length) this.tipoHabitacionId = items[0].id; },
      error: () => { this.error.set('No se pudo cargar el catálogo. Comprueba que el backend esté activo.'); this.cargando.set(false); },
    });
  }

  agregarAlCarrito(habitacion: CarritoItem): void {
    this.carrito.update((items) => [...items, habitacion]);
    this.guardarCarrito(); this.aviso.set(`${habitacion.nombre} se agregó al carrito.`); this.error.set('');
  }

  quitarDelCarrito(indice: number): void {
    this.carrito.update((items) => items.filter((_, i) => i !== indice)); this.guardarCarrito();
  }

  totalCarrito(): number { return this.carrito().reduce((suma, item) => suma + item.tarifaBase, 0); }
  nochesEstadia(): number {
    if (!this.fechaIngreso || !this.fechaSalida) return 0;
    return Math.max(0, Math.round((Date.parse(`${this.fechaSalida}T00:00:00Z`) - Date.parse(`${this.fechaIngreso}T00:00:00Z`)) / 86_400_000));
  }
  totalEstadia(): number { return this.totalCarrito() * this.nochesEstadia(); }
  irPagina(pagina: PaginaSitio): void {
    this.pagina.set(pagina); this.vista.set('sitio'); this.error.set(''); this.aviso.set('');
    if (pagina === 'catalogo') this.cargarHabitaciones(this.fechaIngreso || undefined, this.fechaSalida || undefined);
    window.scrollTo(0, 0);
  }
  buscarDisponibilidad(): void {
    if (!this.fechaIngreso || !this.fechaSalida || this.fechaSalida <= this.fechaIngreso) {
      this.error.set('La salida debe ser posterior al ingreso.'); return;
    }
    this.irPagina('catalogo');
  }
  mostrarCheckout(): void {
    if (!this.carrito().length) { this.error.set('Agrega una habitación antes de continuar.'); return; }
    this.error.set(''); this.reserva.set(null); this.tokenPago.set(null); this.pagoConfirmado.set(false); this.tokenIngresado = '';
    this.vista.set('checkout'); window.scrollTo(0, 0);
  }

  reservar(): void {
    if (this.enviandoReserva()) return;
    this.error.set(''); this.aviso.set(''); this.reserva.set(null);
    this.enviandoReserva.set(true);
    const items = this.carrito().map((habitacion) => ({ tipoHabitacionId: habitacion.id, cantidad: 1,
      fechaIngreso: this.fechaIngreso, fechaSalida: this.fechaSalida, huespedesPorHabitacion: this.huespedes, serviciosIds: [] }));
    this.http.post<ReservaPublica>(`${this.api}/reservas`, { items,
      codigoCupon: null, titular: { nombre: this.nombre, documento: this.documento, correo: this.correo, telefono: this.telefono } }).subscribe({
      next: (resultado) => {
        this.reserva.set(resultado);
        this.enviandoReserva.set(false);
        if (resultado.estado === 'CONFIRMADA') {
          this.pagoConfirmado.set(true);
          this.limpiarCarrito();
        } else {
          this.generarTokenPago(resultado.id);
        }
      },
      error: (error) => { this.error.set(this.errorApi(error)); this.enviandoReserva.set(false); },
    });
  }

  generarTokenPago(reservaId = this.reserva()?.id): void {
    if (!reservaId || this.enviandoReserva()) return;
    this.error.set(''); this.enviandoReserva.set(true);
    this.http.post<TokenPagoResponse>(`${this.api}/pagos/ficticio/token`, { reservaId }).subscribe({
      next: (token) => { this.tokenPago.set(token); this.tokenIngresado = ''; this.enviandoReserva.set(false); },
      error: (error) => { this.error.set(this.errorApi(error)); this.enviandoReserva.set(false); },
    });
  }

  validarTokenPago(): void {
    const actual = this.reserva();
    if (!actual || this.enviandoPago()) return;
    this.error.set(''); this.enviandoPago.set(true);
    this.http.post<PagoFicticioResponse>(`${this.api}/pagos/ficticio/validar`, {
      reservaId: actual.id, token: this.tokenIngresado.trim(),
    }).subscribe({
      next: (resultado) => {
        this.reserva.update((reserva) => reserva ? { ...reserva, estado: resultado.estadoReserva } : reserva);
        this.pagoConfirmado.set(resultado.estadoPago === 'APROBADO');
        this.tokenPago.set(null); this.tokenIngresado = ''; this.enviandoPago.set(false);
        this.limpiarCarrito(); this.cargarReservas(); this.aviso.set(resultado.mensaje);
      },
      error: (error) => { this.error.set(this.errorApi(error)); this.enviandoPago.set(false); },
    });
  }

  private limpiarCarrito(): void { this.carrito.set([]); this.guardarCarrito(); }

  iniciarSesion(): void {
    if (this.iniciandoSesion()) return;
    this.iniciandoSesion.set(true);
    this.error.set('');
    this.http.post<{token: string}>(`${this.api}/auth/login`, { usuario: this.usuario, contrasena: this.contrasena }).subscribe({
      next: (resultado) => { this.token = resultado.token; sessionStorage.setItem('hotelCostaToken', this.token); this.usuario = ''; this.contrasena = ''; this.vista.set('admin'); this.cargarAdmin(); this.iniciandoSesion.set(false); },
      error: (error) => { this.error.set(this.errorApi(error)); this.iniciandoSesion.set(false); },
    });
  }

  cerrarSesion(): void { this.token = ''; sessionStorage.removeItem('hotelCostaToken'); this.vista.set('sitio'); this.adminVista.set('resumen'); this.error.set(''); }

  abrirAdmin(vista: VistaAdmin): void { this.adminVista.set(vista); this.error.set(''); this.aviso.set(''); this.cargarAdmin(); }

  private cargarAdmin(): void {
    if (!this.token) return;
    this.http.get<Record<string, number>>(`${this.api}/admin/resumen`, { headers: this.headers() }).subscribe({ next: (data) => this.adminResumen.set(data), error: (e) => this.error.set(this.errorApi(e)) });
    this.cargarReservas();
    this.http.get<Record<string, string>>(`${this.api}/admin/habitaciones/estado`, { headers: this.headers() }).subscribe({ next: (data) => { this.estadosHabitaciones.set(data); Object.assign(this.estadoPorHabitacion, data); } });
    this.cargarMapaOcupacion();
    this.http.get<Record<string, string | number>>(`${this.api}/admin/reportes`, { headers: this.headers() }).subscribe({ next: (data) => this.reporte.set(data) });
    this.http.get<Record<string, string>>(`${this.api}/admin/contenido`, { headers: this.headers() }).subscribe({ next: (data) => this.contenido.set(data) });
  }

  cargarReservas(): void {
    if (!this.token) return;
    this.http.get<ReservaPublica[]>(`${this.api}/admin/reservas`, { headers: this.headers() }).subscribe({ next: (data) => this.reservas.set(data) });
  }

  cargarMapaOcupacion(): void {
    if (!this.token) return;
    const desde = new Date().toISOString().slice(0, 10);
    const params = new HttpParams().set('desde', desde).set('dias', '7');
    this.http.get<MapaOcupacion>(`${this.api}/admin/ocupacion`, { headers: this.headers(), params })
      .subscribe({ next: (mapa) => this.mapaOcupacion.set(mapa), error: (error) => this.error.set(this.errorApi(error)) });
  }

  cambiarEstadoReserva(reserva: ReservaPublica, estado: string): void {
    this.http.patch<ReservaPublica>(`${this.api}/admin/reservas/${reserva.id}/estado`, { estado }, { headers: this.headers() }).subscribe({
      next: () => { this.aviso.set(`Reserva ${reserva.codigo}: ${estado.toLowerCase()}.`); this.cargarAdmin(); }, error: (e) => this.error.set(this.errorApi(e)),
    });
  }

  guardarEstadoHabitacion(id: number): void {
    const estado = this.estadoPorHabitacion[id];
    this.http.put(`${this.api}/admin/habitaciones/${id}/estado`, { estado }, { headers: this.headers() }).subscribe({
      next: () => { this.aviso.set('Estado de habitación actualizado.'); this.cargarAdmin(); this.cargarHabitaciones(this.fechaIngreso || undefined, this.fechaSalida || undefined); }, error: (e) => this.error.set(this.errorApi(e)),
    });
  }

  guardarContenido(): void {
    this.http.put<Record<string, string>>(`${this.api}/admin/contenido`, this.contenido(), { headers: this.headers() }).subscribe({
      next: (data) => { this.contenido.set(data); this.aviso.set('Contenido actualizado.'); }, error: (e) => this.error.set(this.errorApi(e)),
    });
  }

  actualizarContenido(campo: string, valor: string): void { this.contenido.update((actual) => ({ ...actual, [campo]: valor })); }

  crearRecepcion(event: Event): void {
    if (this.enviandoRecepcion()) return;
    event.preventDefault(); this.error.set(''); this.aviso.set('');
    this.enviandoRecepcion.set(true);
    const habitacion = this.habitaciones().find((item) => item.id === this.tipoHabitacionId);
    if (!habitacion) { this.error.set('Selecciona una habitación.'); this.enviandoRecepcion.set(false); return; }
    const body = { items: [{ tipoHabitacionId: habitacion.id, cantidad: 1, fechaIngreso: this.fechaIngreso, fechaSalida: this.fechaSalida, huespedesPorHabitacion: this.huespedes, serviciosIds: [] }],
      codigoCupon: null, titular: { nombre: this.nombre, documento: this.documento, correo: this.correo, telefono: this.telefono } };
    this.http.post<ReservaPublica>(`${this.api}/admin/recepcion`, body, { headers: this.headers() }).subscribe({
      next: (data) => { this.aviso.set(`Ingreso registrado: ${data.codigo}`); this.cargarAdmin(); this.enviandoRecepcion.set(false); }, error: (e) => { this.error.set(this.errorApi(e)); this.enviandoRecepcion.set(false); },
    });
  }

  exportarReporte(): void {
    const csv = `Métrica,Valor\nReservas,${this.reporte()['reservas'] ?? 0}\nIngresos estimados,${this.reporte()['ingresosEstimados'] ?? 0}\nCanal principal,${this.reporte()['canalPrincipal'] ?? ''}`;
    const url = URL.createObjectURL(new Blob([csv], { type: 'text/csv;charset=utf-8' }));
    const enlace = document.createElement('a'); enlace.href = url; enlace.download = 'reporte-hotel.csv'; enlace.click(); URL.revokeObjectURL(url);
  }
}
