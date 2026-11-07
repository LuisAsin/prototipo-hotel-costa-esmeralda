import { ModalidadServicio, TipoPromocion } from './enums';

export interface ItemCarritoRequest {
  tipoHabitacionId: number;
  cantidad: number;
  fechaIngreso: string;
  fechaSalida: string;
  huespedesPorHabitacion: number;
  serviciosIds: number[];
}

export interface CotizacionRequest {
  items: ItemCarritoRequest[];
  codigoCupon: string | null;
}

export interface ServicioCotizado {
  servicioId: number;
  nombre: string;
  modalidad: ModalidadServicio;
  /** noches o 1 */
  cantidad: number;
  importe: number;
}

export interface SugerenciaTipo {
  tipoHabitacionId: number;
  nombreTipo: string;
  disponibles: number;
}

export interface ItemCotizacion {
  indice: number;
  tipoHabitacionId: number;
  nombreTipo: string;
  cantidad: number;
  noches: number;
  tarifaNoche: number;
  importeHabitaciones: number;
  servicios: ServicioCotizado[];
  importeServicios: number;
  disponible: boolean;
  disponibles: number;
  mensaje: string | null;
  sugerencias: SugerenciaTipo[];
}

/** En la cotización el descuento es un objeto (en las reservas es un número). */
export interface DescuentoAplicado {
  promocionId: number;
  tipo: TipoPromocion;
  nombre: string;
  monto: number;
}

export interface CotizacionResponse {
  items: ItemCotizacion[];
  subtotalHabitaciones: number;
  subtotalServicios: number;
  subtotal: number;
  descuento: DescuentoAplicado | null;
  total: number;
  avisos: string[];
  todoDisponible: boolean;
}
