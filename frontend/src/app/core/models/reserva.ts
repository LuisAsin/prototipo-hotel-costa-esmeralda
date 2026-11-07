import { ItemCarritoRequest } from './carrito-cotizacion';
import { Canal, EstadoDetalle, EstadoReserva, ModalidadConfirmacion, ModalidadServicio, TipoEntidadHistorial } from './enums';
import { PagoResponse } from './pago';
import { ComprobanteResponse } from './comprobante';

export interface TitularRequest {
  nombre: string;
  documento: string;
  correo: string;
  telefono: string;
}

export interface CrearReservaRequest {
  items: ItemCarritoRequest[];
  codigoCupon: string | null;
  titular: TitularRequest;
}

export interface ServicioReservaResponse {
  id: number;
  servicioId: number;
  nombre: string;
  modalidad: ModalidadServicio;
  precioUnitario: number;
  cantidad: number;
  importe: number;
}

export interface DetalleReservaResponse {
  id: number;
  habitacionId: number;
  habitacionNumero: string;
  tipoHabitacionId: number;
  nombreTipo: string;
  fechaIngreso: string;
  fechaSalida: string;
  noches: number;
  cantidadHuespedes: number;
  tarifaNoche: number;
  importeHabitacion: number;
  estado: EstadoDetalle;
  cancelable: boolean;
  motivoNoCancelable: string | null;
  servicios: ServicioReservaResponse[];
}

/** En las reservas el descuento es un número + descripcionDescuento. */
export interface ReservaResponse {
  id: number;
  codigo: string;
  estado: EstadoReserva;
  canal: Canal;
  modalidad: ModalidadConfirmacion;
  venceEn: string | null;
  segundosRestantes: number | null;
  garantiaHasta: string | null;
  detalles: DetalleReservaResponse[];
  subtotalHabitaciones: number;
  subtotalServicios: number;
  subtotal: number;
  descuento: number;
  descripcionDescuento: string | null;
  total: number;
  comprobanteNumero: string | null;
  avisos: string[];
}

export interface HistorialResponse {
  id: number;
  tipoEntidad: TipoEntidadHistorial;
  entidadId: number;
  estadoAnterior: string | null;
  estadoNuevo: string;
  usuarioId: number | null;
  /** «Sistema» si es el job */
  usuarioNombre: string;
  fecha: string;
  motivo: string;
}

export interface ReservaDetalleResponse {
  id: number;
  codigo: string;
  estado: EstadoReserva;
  canal: Canal;
  modalidad: ModalidadConfirmacion;
  titularId: number;
  titularNombre: string;
  titularDocumento: string;
  titularCorreo: string;
  titularTelefono: string;
  registradoPorId: number;
  registradoPorNombre: string;
  fechaCreacion: string;
  fechaConfirmacion: string | null;
  venceEn: string | null;
  segundosRestantes: number | null;
  garantiaHasta: string | null;
  subtotalHabitaciones: number;
  subtotalServicios: number;
  subtotal: number;
  descuento: number;
  descripcionDescuento: string | null;
  total: number;
  totalPagado: number;
  penalidad: number;
  montoDevolucion: number;
  cancelable: boolean;
  motivoNoCancelable: string | null;
  estanciaIniciada: boolean;
  permiteNoPresentacion: boolean;
  detalles: DetalleReservaResponse[];
  pagos: PagoResponse[];
  historial: HistorialResponse[];
  /** No es null si se emitió, también en reservas CANCELADAS (D13). */
  comprobante: ComprobanteResponse | null;
}

export interface ReservaResumenResponse {
  id: number;
  codigo: string;
  estado: EstadoReserva;
  canal: Canal;
  modalidad: ModalidadConfirmacion;
  titularNombre: string;
  /** mínima */
  fechaIngreso: string;
  /** máxima */
  fechaSalida: string;
  habitaciones: string[];
  total: number;
  fechaCreacion: string;
  venceEn: string | null;
  garantiaHasta: string | null;
  cancelable: boolean;
  permiteNoPresentacion: boolean;
}
