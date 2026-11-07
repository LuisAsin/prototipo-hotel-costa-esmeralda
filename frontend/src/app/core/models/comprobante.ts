import { Canal, EstadoReserva, ModalidadConfirmacion, ModalidadServicio, TipoAnotacion } from './enums';

/** Valores al emitir (copia inmutable, D13b). */
export interface ComprobanteHabitacion {
  habitacionNumero: string;
  nombreTipo: string;
  fechaIngreso: string;
  fechaSalida: string;
  noches: number;
  cantidadHuespedes: number;
  tarifaNoche: number;
  importeHabitacion: number;
}

/** Valores al emitir. */
export interface ComprobanteServicio {
  habitacionNumero: string;
  nombre: string;
  modalidad: ModalidadServicio;
  precioUnitario: number;
  cantidad: number;
  importe: number;
}

export interface AnotacionComprobante {
  fecha: string;
  tipo: TipoAnotacion;
  descripcion: string;
  importeCancelado: number;
  penalidad: number;
  montoDevolucion: number;
  nuevoTotal: number;
  usuarioNombre: string;
}

export interface ComprobanteResponse {
  // Copia inmutable de la emisión
  numero: string;
  fechaEmision: string;
  reservaId: number;
  codigoReserva: string;
  titularNombre: string;
  titularDocumento: string;
  titularCorreo: string;
  titularTelefono: string;
  canal: Canal;
  modalidad: ModalidadConfirmacion;
  habitaciones: ComprobanteHabitacion[];
  servicios: ComprobanteServicio[];
  subtotalHabitaciones: number;
  subtotalServicios: number;
  subtotal: number;
  descuento: number;
  descripcionDescuento: string | null;
  total: number;
  /** «Comprobante interno — no es comprobante electrónico tributario» */
  rotulo: string;
  // Estado actual, fuera de la copia
  estadoReserva: EstadoReserva;
  anotaciones: AnotacionComprobante[];
}
